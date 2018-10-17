package zm.gov.moh.smartcerv.component.login.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.schedulers.Schedulers;
import zm.gov.moh.core.model.AuthInfo;
import zm.gov.moh.core.util.RestAPIImpl;
import zm.gov.moh.core.util.Util;
import zm.gov.moh.core.util.UtilImpl;
import zm.gov.moh.smartcerv.component.Submodules.view.SubModulesActivity;
import zm.gov.moh.smartcerv.component.login.model.Credintials;


public class LoginViewModel extends AndroidViewModel {

    private Util util;
    private RestAPIImpl restAPI;
    Context context;

    public LoginViewModel(Application application){
        super(application);

        context = application;

        util = new UtilImpl();
        restAPI = new RestAPIImpl();
    }

    public void submitCredentials(Credintials credintials){

        String  cred = util.credentialsToBase64(credintials.getUsername(), credintials.getPassword());

        restAPI.session(cred)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onExceptionResumeNext(Maybe.empty())
                    .subscribe(rese -> {

                        AuthInfo authInfo = rese;
                        authInfo.toString();
                        showViewIfAuthorized();
                    });
    }

    public void showViewIfAuthorized(){

        context.startActivity(new Intent(context,SubModulesActivity.class));
    }





}