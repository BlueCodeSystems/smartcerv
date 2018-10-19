package zm.gov.moh.core.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import zm.gov.moh.core.model.Authentication;
import zm.gov.moh.core.utils.RestAPI;
import zm.gov.moh.core.utils.Utils;

public class RestServiceImpl implements RestAPIService {

    RestAPI restAPI;

    public RestServiceImpl(RestAPI restAPI) {

        this.restAPI = restAPI;
    }


    public void session(Context context, String credentials, final Function<Authentication, Void> success) {

        ProgressDialog progressDialog = Utils.showProgressDialog(context, context.getResources().getString(zm.gov.moh.core.R.string.please_wait));


        if (Utils.checkInternetConnectivity(context)) {

            progressDialog.show();

            restAPI.session(credentials)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(

                            authentication -> {

                                success.apply(authentication);
                                progressDialog.dismiss();
                            },
                            exception -> {

                                if (exception instanceof HttpException) {

                                    HttpException httpException = (HttpException) exception;

                                    if (httpException.code() == 401) {
                                        progressDialog.dismiss();
                                        Utils.showModelDialog(context, "Authentication failed", "Check if credentials were entered correctly").show();
                                    }
                                }

                            });
        }
        else
            Utils.showSnackBar(context, context.getResources().getString(zm.gov.moh.core.R.string.no_internet), android.R.color.holo_orange_light, Snackbar.LENGTH_LONG);

    }
}
