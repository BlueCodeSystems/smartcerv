package zm.gov.moh.common.component.login.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import zm.gov.moh.common.component.login.model.AuthenticationStatus;
import zm.gov.moh.core.service.RestAPIService;
import zm.gov.moh.core.utils.Utils;
import zm.gov.moh.common.component.login.model.Credentials;


public class LoginViewModel extends AndroidViewModel {

    private RestAPIService restAPIService;
    private Credentials credentials;
    private MutableLiveData<AuthenticationStatus> authenticationStatus;
    private AtomicBoolean pending  = new  AtomicBoolean(true);
    private Application application;

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
                credentials.clear();

                authenticationStatus.setValue(AuthenticationStatus.PENDING);

                restAPIService.session(credintialsBase64,

                        //onSuccess
                        authentication -> {

                            pending.set(true);
                            authenticationStatus.setValue(AuthenticationStatus.AUTHORIZED);
                        },

                        //onFailure
                        throwable -> {

                            pending.set(true);
                            if (throwable instanceof HttpException) {

                                HttpException httpException = (HttpException) throwable;

                                if (httpException.code() == 401)
                                    authenticationStatus.setValue(AuthenticationStatus.UNAUTHORIZED);
                            } else if (throwable instanceof TimeoutException)
                                authenticationStatus.setValue(AuthenticationStatus.TIMEOUT);
                            else
                                authenticationStatus.setValue(AuthenticationStatus.UNREACHABLE_SERVER);
                        });
            } else
                authenticationStatus.setValue(AuthenticationStatus.NO_INTERNET);
        }
        else
            authenticationStatus.setValue(AuthenticationStatus.NO_CREDENTIALS);
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setRestAPIService(RestAPIService restAPIService) {
        this.restAPIService = restAPIService;
    }

    public MutableLiveData<AuthenticationStatus> getAuthenticationStatus() {

        if(authenticationStatus == null)
            authenticationStatus = new MutableLiveData<>();
        return authenticationStatus;
    }


    public AtomicBoolean getPending() {
        return pending;
    }

    @Override
    protected void onCleared() {

        super.onCleared();
        restAPIService.onClear();
    }
}