package zm.gov.moh.smartcerv.component.login.viewmodel;

import android.app.Application;
import android.app.ProgressDialog;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import zm.gov.moh.core.model.Authentication;
import zm.gov.moh.core.service.RestAPIService;
import zm.gov.moh.core.service.RestServiceImpl;
import zm.gov.moh.core.utils.Provider;
import zm.gov.moh.core.utils.Utils;
import zm.gov.moh.smartcerv.component.submodules.view.SubModulesActivity;
import zm.gov.moh.smartcerv.component.login.model.Credintials;


public class LoginViewModel extends AndroidViewModel {

    private Utils util;
    private RestAPIService restAPIService;
    private Credintials credentials;
    private Context activity;
    Context context;

    public Context getContext() {
        return context;
    }

    public LoginViewModel(Application application){
        super(application);

        context = application;
        credentials = new Credintials();
        credentials.setUsername("admin");
        credentials.setPassword("Openmrs4Bluecode");
        restAPIService = new RestServiceImpl(Provider.getRestAPI());

    }


    public void submitCredentials(Credintials credintials){

        String credintialsBase64 = Utils.credentialsToBase64(credintials.getUsername(),credintials.getPassword());

        restAPIService.session(activity, credintialsBase64, authentication -> {

            showViewIfAuthorized();
            return null;
        });


    }

    public void showViewIfAuthorized(){

        context.startActivity(new Intent(context,SubModulesActivity.class));
    }

    public Credintials getCredentials() {
        return credentials;
    }

    public void setRestAPIService(RestAPIService restAPIService) {
        this.restAPIService = restAPIService;
    }

    public void setUtil(Utils util) {
        this.util = util;
    }

    public void setActivity(Context activity){

        this.activity = activity;
    }
}