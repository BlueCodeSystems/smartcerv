package zm.gov.moh.common.component.login.view;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import zm.gov.moh.core.service.RestServiceImpl;
import zm.gov.moh.core.utils.ClassHolder;
import zm.gov.moh.core.utils.Provider;
import zm.gov.moh.core.utils.Utils;
import zm.gov.moh.common.BR;
import zm.gov.moh.common.R;
import zm.gov.moh.common.component.login.viewmodel.LoginViewModel;
import zm.gov.moh.common.databinding.LoginActivityBinding;


public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;



        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.setRestAPIService(new RestServiceImpl(Provider.getRestAPI()));


        ClassHolder classHolder = (ClassHolder) getIntent().getSerializableExtra(ClassHolder.KEY);

        LoginActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.login_activity);

        binding.setCredentials(loginViewModel.getCredentials());
        binding.setVariable(BR.viewmodel, loginViewModel);


        final  Observer<String> submittedCredentialsObserver = credentials -> {

            if(loginViewModel.getPending().compareAndSet(true, false)) {

                if(Utils.checkInternetConnectivity(context)) {

                    ProgressDialog progressDialog = Utils.showProgressDialog(context, context.getResources().getString(zm.gov.moh.core.R.string.please_wait));
                    progressDialog.show();

                    loginViewModel.getRestAPIService().session(credentials,

                            authentication -> {

                                context.startActivity(new Intent(context, classHolder.getClassInstance()));
                                progressDialog.dismiss();
                            },

                            throwable -> {
                                progressDialog.dismiss();
                                if (throwable instanceof HttpException) {

                                    HttpException httpException = (HttpException) throwable;

                                    if (httpException.code() == 401)
                                        Utils.showModelDialog(context, "Authentication failed", "Please check if credentials were entered correctly").show();

                                }
                                else if(throwable instanceof TimeoutException)
                                    Utils.showModelDialog(context, "Request Timeout","The request to the server has been timeout. please try again later.").show();
                                else
                                    Utils.showModelDialog(context, "Connectivity problem","There was a problem tying to connect to the server. Try again later").show();
                            });
                }
                else
                    Utils.showSnackBar(context, context.getResources().getString(zm.gov.moh.core.R.string.no_internet), android.R.color.holo_orange_light, Snackbar.LENGTH_LONG);

            }
        };


        loginViewModel.getSubmittedCredentials().observe(this, submittedCredentialsObserver);

    }
}