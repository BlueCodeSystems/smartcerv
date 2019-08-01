package zm.gov.moh.common.submodule.login.view;

import android.app.ProgressDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Resources;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import android.widget.Toast;
import zm.gov.moh.common.submodule.login.model.ViewState;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.service.ServiceManager;
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
    private Toast exitToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        resources = context.getResources();

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        this.viewModel = loginViewModel;


        progressDialog = Utils.showProgressDialog(context, context.getResources().getString(zm.gov.moh.common.R.string.please_wait));

        Bundle bundle = getIntent().getExtras();

        Module nextModule = (Module)bundle.getSerializable(START_SUBMODULE_KEY);

        bundle.remove(START_SUBMODULE_KEY);

        getSupportFragmentManager().beginTransaction().replace(R.id.segment, new CredentialFragment()).commit();

        LoginActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.login_activity);

        binding.setVariable(BR.viewmodel, loginViewModel);
        binding.setVariable(BR.toolbarhandler, getToolbarHandler(this));
        binding.setContext(this);

        final Observer<ViewState> viewStateObserver = state -> {

            if(loginViewModel.getPending().compareAndSet(true, false) && state != null) {

                switch (state){

                    case AUTHORIZED:
                        startModule(nextModule);
                        progressDialog.dismiss();

                        ServiceManager.getInstance(this)
                                .setService(ServiceManager.Service.PULL_PATIENT_ID_REMOTE)
                                .startOnComplete(ServiceManager.Service.PULL_PATIENT_ID_REMOTE, ServiceManager.Service.PULL_META_DATA_REMOTE)
                                .startOnComplete(ServiceManager.Service.PULL_META_DATA_REMOTE,ServiceManager.Service.PUSH_ENTITY_REMOTE)
                                .start();
                        finish();
                        break;

                    case UNAUTHORIZED:
                        Utils.showModelDialog(context, resources.getString(zm.gov.moh.common.R.string.authentication_failed), resources.getString(zm.gov.moh.common.R.string.incorrect_credentials)).show();
                        progressDialog.dismiss();
                        break;

                    case UNAUTHORIZED_LOCATION:
                        Utils.showModelDialog(context, resources.getString(zm.gov.moh.common.R.string.authentication_failed), resources.getString(zm.gov.moh.common.R.string.unauthorized_location)).show();
                        progressDialog.dismiss();
                        break;

                    case USER_NOT_PROVIDER:
                        Utils.showModelDialog(context, resources.getString(zm.gov.moh.common.R.string.authentication_failed), resources.getString(R.string.user_not_provider)).show();
                        progressDialog.dismiss();
                        break;

                    case PENDING:
                        progressDialog.show();
                        loginViewModel.getViewBindings().clearCredentials();
                        break;

                    case NO_INTERNET:
                        Utils.showSnackBar(context, resources.getString(zm.gov.moh.common.R.string.no_internet), android.R.color.holo_orange_light, Snackbar.LENGTH_LONG);
                        break;

                    case NO_CREDENTIALS:
                        Utils.showSnackBar(context, resources.getString(zm.gov.moh.common.R.string.no_credentials), android.R.color.holo_orange_light, Snackbar.LENGTH_LONG);
                        break;

                    case TIMEOUT:
                        Utils.showModelDialog(context, resources.getString(zm.gov.moh.common.R.string.request_timeout), resources.getString(zm.gov.moh.common.R.string.server_request_timeout)).show();
                        progressDialog.dismiss();
                        break;

                    case UNREACHABLE_SERVER:
                        Utils.showModelDialog(context, resources.getString(zm.gov.moh.common.R.string.connection_problem), resources.getString(zm.gov.moh.common.R.string.problem_connecting_server)).show();
                        progressDialog.dismiss();
                        break;

                    case MULTIPLE_LOCATION_SELECTION:
                        getSupportFragmentManager().beginTransaction().replace(R.id.segment, new LocationFragment()).commit();
                        progressDialog.dismiss();

                    default: break;
                }
            }
        };

        loginViewModel.getViewState().observe(this, viewStateObserver);
    }

    @Override
    public void onBackPressed() {

        if (exitToast == null || exitToast.getView() == null || exitToast.getView().getWindowToken() == null) {
            exitToast = Toast.makeText(this, "Press again to exit", Toast.LENGTH_LONG);
            exitToast.show();
        } else {
            exitToast.cancel();
            this.finishAffinity();

        }
    }
}