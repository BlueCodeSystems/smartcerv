package zm.gov.moh.common.submodule.login.viewmodel;

import android.app.Application;
import android.util.Base64;

import androidx.lifecycle.MutableLiveData;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.security.MessageDigest;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import zm.gov.moh.common.submodule.login.model.AuthenticationStatus;
import zm.gov.moh.core.model.Authentication;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.ConcurrencyUtils;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.Utils;
import zm.gov.moh.common.submodule.login.model.Credentials;


public class LoginViewModel extends BaseAndroidViewModel implements InjectableViewModel {

    private Credentials credentials;
    private final String AES = "AES";
    private MutableLiveData<AuthenticationStatus> authenticationStatus;
    private AtomicBoolean pending  = new  AtomicBoolean(true);
    private Application application;
    private final int TIMEOUT = 30000;
    private String credentialsToBase64;

    public LoginViewModel(Application application){
        super(application);

        this.application = application;
        credentials = new Credentials();
    }


    public void submitCredentials(Credentials credentials){

        pending.set(true);

        credentials.setUsername("anthony");
        credentials.setPassword("Openmrs3");

        if(credentials.getUsername() != "" && credentials.getPassword() != "") {

            credentialsToBase64 = Utils.credentialsToBase64(credentials.getUsername(), credentials.getPassword());

            //Online authentication
            if (Utils.checkInternetConnectivity(application)) {

                authenticationStatus.setValue(AuthenticationStatus.PENDING);

                ConcurrencyUtils.consume(
                        this::onSuccess,
                        this::onError,
                        getRepository().getRestApi().session(credentialsToBase64),
                        TIMEOUT);
            }
            else {
                loginOffline(credentialsToBase64);
            }
        }
        else
            authenticationStatus.setValue(AuthenticationStatus.NO_CREDENTIALS);
    }

    public Credentials getCredentials() {
        return credentials;
    }


    public MutableLiveData<AuthenticationStatus> getAuthenticationStatus() {

        if(authenticationStatus == null)
            authenticationStatus = new MutableLiveData<>();
        return authenticationStatus;
    }

    public AtomicBoolean getPending() {
        return pending;
    }

    public void saveSessionLocation(Location location){

        getRepository().getDefaultSharePrefrences().edit().putLong(
                application.getResources().getString(zm.gov.moh.core.R.string.session_location_key),
                location.getLocationId())
                .apply();
    }

    private void onSuccess(Authentication authentication){

        String accessToken = authentication.getToken();
        getRepository().getDefaultSharePrefrences()
                .edit()
                .putString(application.getResources().getString(zm.gov.moh.core.R.string.logged_in_user_uuid_key), authentication.getUserUuid())
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
        authenticationStatus.setValue(AuthenticationStatus.AUTHORIZED);
    }

    private void onError(Throwable throwable){

        pending.set(true);
        if (throwable instanceof HttpException) {

            HttpException httpException = (HttpException) throwable;

            if (httpException.code() == 401)
                authenticationStatus.setValue(AuthenticationStatus.UNAUTHORIZED);
        }
        else if (throwable instanceof TimeoutException)
            authenticationStatus.setValue(AuthenticationStatus.TIMEOUT);
        else
            authenticationStatus.setValue(AuthenticationStatus.UNREACHABLE_SERVER);
    }

    public void loginOffline(String key){

        String accessToken = getRepository().getDefaultSharePrefrences().getString(Key.ACCESS_TOKEN,null);
        String accessTokenEncrypted = getRepository().getDefaultSharePrefrences().getString(Key.ACCESS_TOKEN_ENCRYPTED,null);

        if(accessToken == null) {
            authenticationStatus.setValue(AuthenticationStatus.NO_INTERNET);
            return;
        }

        try {

            Cipher cipher = Cipher.getInstance(AES);
            java.security.Key aesKey = new SecretKeySpec(to128bit(key), AES);
            cipher.init(Cipher.DECRYPT_MODE, aesKey);

            byte[] decrypted = cipher.doFinal(Base64.decode(accessTokenEncrypted,Base64.DEFAULT));

            if(accessToken.equals(new String(decrypted)))
                authenticationStatus.setValue(AuthenticationStatus.AUTHORIZED);
            else
                authenticationStatus.setValue(AuthenticationStatus.UNAUTHORIZED);


        }catch (Exception e){
            authenticationStatus.setValue(AuthenticationStatus.UNAUTHORIZED);
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

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}