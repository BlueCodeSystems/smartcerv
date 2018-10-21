package zm.gov.moh.core.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import zm.gov.moh.core.model.Authentication;
import zm.gov.moh.core.utils.RestAPI;
import zm.gov.moh.core.utils.Utils;

public class RestServiceImpl implements RestAPIService {

    private RestAPI restAPI;
    private Disposable disposable;

    public RestServiceImpl(RestAPI restAPI) {

        this.restAPI = restAPI;
    }


    public void session(final String credentials, final Consumer<Authentication> success, final Consumer<Throwable> failure) {

           disposable = restAPI.session(credentials)
                    .timeout(30000,TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(success, failure);

    }

    public void onClear(){
        disposable.dispose();
    }
}
