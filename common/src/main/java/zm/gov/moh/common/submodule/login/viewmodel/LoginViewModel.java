package zm.gov.moh.common.submodule.login.viewmodel;

import android.app.Application;
import android.util.Base64;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.security.MessageDigest;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import zm.gov.moh.common.submodule.login.model.ViewBindings;
import zm.gov.moh.common.submodule.login.model.ViewState;
import zm.gov.moh.core.model.Authentication;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.User;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.ConcurrencyUtils;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.Utils;


public class LoginViewModel extends BaseAndroidViewModel implements InjectableViewModel {

    private ViewBindings viewBindings;
    private final String AES = "AES";
    private MutableLiveData<ViewState> viewState;
    private AtomicBoolean pending  = new  AtomicBoolean(true);
    private Application application;
    private final int TIMEOUT = 30000;
    private String credentialsToBase64;
    private Location[] providerLocations;

    public LoginViewModel(Application application){
        super(application);

        this.application = application;
        viewBindings = new ViewBindings();
    }


    public void submitCredentials(ViewBindings credentials){

        pending.set(true);


        if(credentials.getUsername() != "" && credentials.getPassword() != "") {

            credentialsToBase64 = Utils.credentialsToBase64(credentials.getUsername(), credentials.getPassword());

            //Online authentication
            if (Utils.checkInternetConnectivity(application)) {

                viewState.setValue(ViewState.PENDING);

                ConcurrencyUtils.consumeAsync(
                        this::onSuccess,
                        throwable-> ConcurrencyUtils.consumeOnMainThread(this::onError, throwable),
                        getRepository().getRestApi().session(credentialsToBase64),
                        TIMEOUT);
            }
            else {
                loginOffline(credentialsToBase64);
            }
        }
        else
            viewState.setValue(ViewState.NO_CREDENTIALS);
    }

    public ViewBindings getViewBindings() {
        return viewBindings;
    }

    public Location[] getProviderLocations() {
        return providerLocations;
    }

    public MutableLiveData<ViewState> getViewState() {

        if(viewState == null)
            viewState = new MutableLiveData<>();
        return viewState;
    }

    public AtomicBoolean getPending() {
        return pending;
    }

    public void saveSessionLocation(Long locationId){

        long currentLocationId = getRepository().getDefaultSharePrefrences().getLong(Key.LOCATION_ID, 0);

        if(locationId != currentLocationId)
            getRepository().getDefaultSharePrefrences().edit().putString(Key.LAST_DATA_SYNC_DATETIME,null).apply();

        getRepository().getDefaultSharePrefrences().edit().putLong(Key.LOCATION_ID, locationId)
                .apply();
        pending.set(true);
        viewState.setValue(ViewState.AUTHORIZED);
        saveLocation(locationId);

    }

    private void onSuccess(Authentication authentication){

        String accessToken = authentication.getToken();
        getRepository().getDefaultSharePrefrences()
                .edit()
                .putString(Key.AUTHORIZED_USER_UUID, authentication.getUserUuid())
                .apply();

        getRepository().getDefaultSharePrefrences()
                .edit()
                .putString(Key.ACCESS_TOKEN, accessToken)
                .apply();

        try {

            Cipher cipher = Cipher.getInstance(AES);
            java.security.Key aesKey = new SecretKeySpec(to128bit(credentialsToBase64), AES);
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);

            byte[] encrypted = cipher.doFinal(accessToken.getBytes());

            getRepository().getDefaultSharePrefrences()
                    .edit()
                    .putString(Key.ACCESS_TOKEN_ENCRYPTED, Base64.encodeToString(encrypted,Base64.DEFAULT))
                    .apply();

        }catch (Exception e){

        }

        pending.set(true);

        ConcurrencyUtils.consumeOnMainThread(state -> viewState.setValue(state),
                authoriseLocationLogin(authentication));
    }

    public void onError(Throwable throwable){

        pending.set(true);
        if (throwable instanceof HttpException) {

            HttpException httpException = (HttpException) throwable;

            if (httpException.code() == 401)
                viewState.setValue(ViewState.UNAUTHORIZED);
        }
        else if (throwable instanceof TimeoutException)
            viewState.setValue(ViewState.TIMEOUT);
        else
            viewState.setValue(ViewState.UNREACHABLE_SERVER);
    }

    public void loginOffline(String key){

        String accessToken = getRepository().getDefaultSharePrefrences().getString(Key.ACCESS_TOKEN,null);
        String accessTokenEncrypted = getRepository().getDefaultSharePrefrences().getString(Key.ACCESS_TOKEN_ENCRYPTED,null);

        if(accessToken == null) {
            viewState.setValue(ViewState.NO_INTERNET);
            return;
        }

        try {

            Cipher cipher = Cipher.getInstance(AES);
            java.security.Key aesKey = new SecretKeySpec(to128bit(key), AES);
            cipher.init(Cipher.DECRYPT_MODE, aesKey);

            byte[] decrypted = cipher.doFinal(Base64.decode(accessTokenEncrypted,Base64.DEFAULT));

            if(accessToken.equals(new String(decrypted)))
                viewState.setValue(ViewState.AUTHORIZED);
            else
                viewState.setValue(ViewState.UNAUTHORIZED);


        }catch (Exception e){
            viewState.setValue(ViewState.UNAUTHORIZED);
        }
    }

    public byte[] to128bit(String passphrase){

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(passphrase.getBytes());

            byte[] digest = md.digest();
            byte[] key = new byte[md.digest().length / 2];

            for(int I = 0; I < key.length; I++){
                key[I] = digest[I];
            }

            return key;
        }catch (Exception e){

            return null;
        }

    }

    public ViewState authoriseLocationLogin(Authentication auth){

        User user = auth.getUser();

            if(user.getProvider() != null && user.getPersonName() != null) {

                db.personNameDao().insert(user.getPersonName());
                db.providerDao().insert(user.getProvider());

                getRepository().getDefaultSharePrefrences()
                        .edit()
                        .putLong(Key.PROVIDER_ID, user.getProvider().getProviderId())
                        .apply();

                getRepository().getDefaultSharePrefrences()
                        .edit()
                        .putLong(Key.USER_ID, user.getUserId())
                        .apply();

                if(user.getLocation().length > 0){

                    db.locationDao().insert(user.getLocation());

                    if (user.getLocation().length > 1) {

                        ConcurrencyUtils.consumeOnMainThread(locations -> {

                            this.providerLocations = locations;
                            viewState.setValue(ViewState.MULTIPLE_LOCATION_SELECTION);
                        }, user.getLocation());

                    } else if (user.getLocation().length == 1) {

                        Long locationId = user.getLocation()[0].getLocationId();
                        getRepository().getDefaultSharePrefrences()
                                .edit()
                                .putLong(Key.LOCATION_ID, locationId)
                                .apply();

                        return ViewState.AUTHORIZED;
                    }
                }
                else
                    return ViewState.UNAUTHORIZED_LOCATION;
        }
        return ViewState.USER_NOT_PROVIDER;

    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }


    public void saveLocation(long lastLocation)
    {

        getRepository().getDefaultSharePrefrences().edit().putLong(Key.LAST_LOCATION,lastLocation).apply();

    }





}