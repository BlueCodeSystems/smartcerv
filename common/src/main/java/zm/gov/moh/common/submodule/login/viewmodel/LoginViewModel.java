package zm.gov.moh.common.submodule.login.viewmodel;

import android.app.Application;
import android.util.Base64;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.security.MessageDigest;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import io.reactivex.Observable;
import zm.gov.moh.common.OpenmrsConfig;
import zm.gov.moh.common.submodule.login.model.ViewState;
import zm.gov.moh.core.model.Authentication;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.derived.ProviderUser;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.repository.database.entity.domain.ProviderAttribute;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.ConcurrencyUtils;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.Utils;
import zm.gov.moh.common.submodule.login.model.Credentials;


public class LoginViewModel extends BaseAndroidViewModel implements InjectableViewModel {

    private Credentials credentials;
    private final String AES = "AES";
    private MutableLiveData<ViewState> viewState;
    private AtomicBoolean pending  = new  AtomicBoolean(true);
    private Application application;
    private final int TIMEOUT = 30000;
    private String credentialsToBase64;
    private List<Location> locations;

    public LoginViewModel(Application application){
        super(application);

        this.application = application;
        credentials = new Credentials();
    }


    public void submitCredentials(Credentials credentials){

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

    public Credentials getCredentials() {
        return credentials;
    }


    public MutableLiveData<ViewState> getViewState() {

        if(viewState == null)
            viewState = new MutableLiveData<>();
        return viewState;
    }

    public AtomicBoolean getPending() {
        return pending;
    }

    public void saveSessionLocation(Location location){

        getRepository().getDefaultSharePrefrences().edit().putLong(Key.LOCATION_ID,
                location.getLocationId())
                .apply();
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
                authoriseLocationLogin(authentication.getUserUuid()));
    }

    private void onError(Throwable throwable){

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

    public ViewState authoriseLocationLogin(String userUuid){

        ProviderUser providerUser =  getRepository().getDatabase().providerUserDao().getByUserUuid(userUuid);

        if(providerUser != null) {

            ProviderAttribute[] providerAttributes = getRepository()
                    .getDatabase()
                    .providerAttributeDao()
                    .getAttributeByTypeBlocking(OpenmrsConfig.PROVIDER_ATTRIBUTE_TYPE_HOME_FACILITY, providerUser.getProviderId());


            if(providerAttributes.length > 1){

                List<String> locationUuids = Observable.fromArray(providerAttributes).map(providerAttribute -> providerAttribute.getValueReference())
                        .toList().blockingGet();

                ConcurrencyUtils.consumeOnMainThread(locations1 -> {

                    this.locations = locations1;
                    viewState.setValue(ViewState.MULTIPLE_LOCATION_SELECTION);
                },getRepository().getDatabase()
                        .locationDao()
                        .getByUuid(locationUuids));
            }
            else if(providerAttributes.length == 1){

                Long locationId = getRepository().getDatabase().locationDao().getIdByUuid(providerAttributes[0].getValueReference());
                getRepository().getDefaultSharePrefrences()
                        .edit()
                        .putLong(Key.LOCATION_ID, locationId)
                        .apply();

                return ViewState.AUTHORIZED;
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
}