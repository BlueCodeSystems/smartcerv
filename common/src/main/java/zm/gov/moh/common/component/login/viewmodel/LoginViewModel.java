package zm.gov.moh.common.component.login.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;

import java.util.concurrent.atomic.AtomicBoolean;

import zm.gov.moh.core.service.RestAPIService;
import zm.gov.moh.core.service.RestServiceImpl;
import zm.gov.moh.core.utils.Provider;
import zm.gov.moh.core.utils.Utils;
import zm.gov.moh.common.component.login.model.Credintials;


public class LoginViewModel extends AndroidViewModel {

    private RestAPIService restAPIService;
    private Credintials credentials;
    private Class submodule;
    private MutableLiveData<Class> submoduleTostart;
    private MutableLiveData<String> submittedCredentials;
    private AtomicBoolean pending  = new  AtomicBoolean(true);


    public LoginViewModel(Application application){
        super(application);

        credentials = new Credintials();
        credentials.setUsername("admin");
        credentials.setPassword("Openmrs4Bluecode");
        restAPIService = new RestServiceImpl(Provider.getRestAPI());
    }


    public void submitCredentials(Credintials credintials){

        String credintialsBase64 = Utils.credentialsToBase64(credintials.getUsername(),credintials.getPassword());
        pending.set(true);
        submittedCredentials.setValue(credintialsBase64);
    }

    public Credintials getCredentials() {
        return credentials;
    }

    public void setRestAPIService(RestAPIService restAPIService) {
        this.restAPIService = restAPIService;
    }

    public MutableLiveData<String> getSubmittedCredentials() {

        if(submittedCredentials == null)
            submittedCredentials = new MutableLiveData<>();
        return submittedCredentials;
    }

    public RestAPIService getRestAPIService() {
        return restAPIService;
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