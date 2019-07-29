package zm.gov.moh.common.submodule.login.view;

import android.app.ProgressDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import zm.gov.moh.common.submodule.login.adapter.LocationArrayAdapter;
import zm.gov.moh.common.submodule.login.model.AuthenticationStatus;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.service.PullMetaDataRemote;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.service.ServiceManager;
import zm.gov.moh.core.utils.Utils;
import zm.gov.moh.common.BR;
import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.login.viewmodel.LoginViewModel;
import zm.gov.moh.common.databinding.LoginActivityBinding;


public class LoginActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private LoginViewModel viewModel;
    private Context context;
    private ProgressDialog progressDialog;
    private Resources resources;
    private ArrayAdapter<Location> locationArrayAdapter;

    //TODO:Must read value from global context
    private final long FACILITY_LOCATION_TAG_ID = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        resources = context.getResources();

        locationArrayAdapter = new LocationArrayAdapter(this, new ArrayList<Location>());

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        progressDialog = Utils.showProgressDialog(context, context.getResources().getString(zm.gov.moh.core.R.string.please_wait));

        Bundle bundle = getIntent().getExtras();

        Module nextModule = (Module)bundle.getSerializable(START_SUBMODULE_KEY);

        bundle.remove(START_SUBMODULE_KEY);

        LoginActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.login_activity);

        Spinner locationsSpinner = findViewById(R.id.locations);
        locationsSpinner.setAdapter(locationArrayAdapter);
        locationsSpinner.setOnItemSelectedListener(this);

        binding.setCredentials(viewModel.getCredentials());
        binding.setVariable(BR.viewmodel, viewModel);
        binding.setVariable(BR.toolbarhandler, getToolbarHandler(this));

        final Observer<AuthenticationStatus> authenticationStatusObserver = status -> {

            if(viewModel.getPending().compareAndSet(true, false) && status != null) {

                switch (status){

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
                        Utils.showModelDialog(context, resources.getString(zm.gov.moh.core.R.string.authentication_failed), resources.getString(zm.gov.moh.core.R.string.incorrect_credentials)).show();
                        progressDialog.dismiss();
                        break;

                    case PENDING:
                        progressDialog.show();
                        viewModel.getCredentials().clear();
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

        viewModel.getAuthenticationStatus().observe(this, authenticationStatusObserver);
        viewModel.getRepository().getDatabase().locationDao().getByTagId(FACILITY_LOCATION_TAG_ID).observe(this, this::setLocation);
    }

    public void setLocation(List<Location> locations){

        locationArrayAdapter.clear();
        locationArrayAdapter.addAll(locations);
        locationArrayAdapter.notifyDataSetChanged();
    }

    boolean doubleBackToExitPressedOnce = false;
    private Toast exitToast;
    @Override
    public void onBackPressed() {
        if (exitToast == null || exitToast.getView() == null || exitToast.getView().getWindowToken() == null) {
            exitToast = Toast.makeText(this, "Press back button again to exit", Toast.LENGTH_LONG);
            exitToast.show();
        } else {
            exitToast.cancel();
            this.finishAffinity();

        }
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Location location = locationArrayAdapter.getItem(i);
        viewModel.saveSessionLocation(location);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}