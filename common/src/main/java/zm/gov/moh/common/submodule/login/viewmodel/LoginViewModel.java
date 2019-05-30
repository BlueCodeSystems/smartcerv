package zm.gov.moh.common.submodule.login.viewmodel;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import zm.gov.moh.common.submodule.login.model.AuthenticationStatus;
import zm.gov.moh.core.model.Authentication;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.Utils;
import zm.gov.moh.common.submodule.login.model.Credentials;


public class LoginViewModel extends BaseAndroidViewModel implements InjectableViewModel {

    private Credentials credentials;
    private MutableLiveData<AuthenticationStatus> authenticationStatus;
    private AtomicBoolean pending  = new  AtomicBoolean(true);
    private Application application;
    private final int TIMEOUT = 30000;

    public LoginViewModel(Application application){
        super(application);

        this.application = application;
        credentials = new Credentials();
    }


    public void submitCredentials(Credentials credentials){

        pending.set(true);


        if(credentials.getUsername() != "" && credentials.getPassword() != "") {

            String credintialsBase64 = Utils.credentialsToBase64(credentials.getUsername(), credentials.getPassword());

            //Online authentication
            if (Utils.checkInternetConnectivity(application)) {

                authenticationStatus.setValue(AuthenticationStatus.PENDING);

                getRepository().consume(
                        this::onSuccess,
                        this::onError,
                        getRepository().getRestApi().session(credintialsBase64),
                        TIMEOUT);
            }
            else
                authenticationStatus.setValue(AuthenticationStatus.NO_INTERNET);
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
                location.location_id)
                .apply();
    }

    private void onSuccess(Authentication authentication){

        getRepository().getDefaultSharePrefrences()
                .edit()
                .putString(application.getResources().getString(zm.gov.moh.core.R.string.logged_in_user_uuid_key), authentication.getUserUuid())
                .apply();

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

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}