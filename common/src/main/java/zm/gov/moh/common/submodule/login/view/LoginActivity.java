package zm.gov.moh.common.submodule.login.view;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import zm.gov.moh.common.submodule.login.model.AuthenticationStatus;
import zm.gov.moh.core.utils.BaseActivity;
import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.utils.Utils;
import zm.gov.moh.common.BR;
import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.login.viewmodel.LoginViewModel;
import zm.gov.moh.common.databinding.LoginActivityBinding;


public class LoginActivity extends BaseActivity {

    private LoginViewModel loginViewModel;
    private Context context;
    private ProgressDialog progressDialog;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        resources = context.getResources();

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        progressDialog = Utils.showProgressDialog(context, context.getResources().getString(zm.gov.moh.core.R.string.please_wait));

        Bundle bundle = getIntent().getExtras();

        Submodule nextSubmodule = (Submodule)bundle.getSerializable(START_SUBMODULE_KEY);

        bundle.remove(START_SUBMODULE_KEY);

        LoginActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.login_activity);

        binding.setCredentials(loginViewModel.getCredentials());
        binding.setVariable(BR.viewmodel, loginViewModel);


        final Observer<AuthenticationStatus> authenticationStatusObserver = status -> {

            if(loginViewModel.getPending().compareAndSet(true, false) && status != null) {

                switch (status){

                    case AUTHORIZED:
                        startSubmodule(nextSubmodule);
                        progressDialog.dismiss();
                        finish();
                        break;

                    case UNAUTHORIZED:
                        Utils.showModelDialog(context, resources.getString(zm.gov.moh.core.R.string.authentication_failed), resources.getString(zm.gov.moh.core.R.string.incorrect_credentials)).show();
                        progressDialog.dismiss();
                        break;

                    case PENDING:
                        progressDialog.show();
                        break;

                    case NO_INTERNET:
                        Utils.showSnackBar(context, resources.getString(zm.gov.moh.core.R.string.no_internet), android.R.color.holo_orange_light, Snackbar.LENGTH_LONG);
                        break;

                    case NO_CREDENTIALS:
                        Utils.showSnackBar(context, resources.getString(zm.gov.moh.core.R.string.no_credentials), android.R.color.holo_orange_light, Snackbar.LENGTH_LONG);
                        break;

                    case TIMEOUT:
                        Utils.showModelDialog(context, resources.getString(zm.gov.moh.core.R.string.request_timeout), resources.getString(zm.gov.moh.core.R.string.server_request_timeout)).show();
                        progressDialog.dismiss();
                        break;

                    case UNREACHABLE_SERVER:
                        Utils.showModelDialog(context, resources.getString(zm.gov.moh.core.R.string.connection_problem), resources.getString(zm.gov.moh.core.R.string.problem_connecting_server)).show();
                        progressDialog.dismiss();
                        break;

                        default: break;
                }
            }
        };

        loginViewModel.getAuthenticationStatus().observe(this, authenticationStatusObserver);
    }
}