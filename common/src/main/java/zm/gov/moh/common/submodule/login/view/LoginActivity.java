package zm.gov.moh.common.submodule.login.view;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import zm.gov.moh.common.submodule.login.adapter.LocationArrayAdapter;
import zm.gov.moh.common.submodule.login.model.AuthenticationStatus;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.service.MetaDataSync;
import zm.gov.moh.core.utils.BaseActivity;
import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.utils.Utils;
import zm.gov.moh.common.BR;
import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.login.viewmodel.LoginViewModel;
import zm.gov.moh.common.databinding.LoginActivityBinding;


public class LoginActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private LoginViewModel loginViewModel;
    private Context context;
    private ProgressDialog progressDialog;
    private Resources resources;
    private ArrayAdapter<Location> locationArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        resources = context.getResources();

        locationArrayAdapter = new LocationArrayAdapter(this, new ArrayList<Location>());

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        progressDialog = Utils.showProgressDialog(context, context.getResources().getString(zm.gov.moh.core.R.string.please_wait));

        Bundle bundle = getIntent().getExtras();

        Submodule nextSubmodule = (Submodule)bundle.getSerializable(START_SUBMODULE_KEY);

        bundle.remove(START_SUBMODULE_KEY);

        LoginActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.login_activity);

        Spinner locationsSpinner = findViewById(R.id.locations);
        locationsSpinner.setAdapter(locationArrayAdapter);
        locationsSpinner.setOnItemSelectedListener(this);

        binding.setCredentials(loginViewModel.getCredentials());
        binding.setVariable(BR.viewmodel, loginViewModel);


        final Observer<AuthenticationStatus> authenticationStatusObserver = status -> {

            if(loginViewModel.getPending().compareAndSet(true, false) && status != null) {

                switch (status){

                    case AUTHORIZED:
                        startSubmodule(nextSubmodule);
                        progressDialog.dismiss();

                        Intent intent = new Intent(this, MetaDataSync.class);
                        startService(intent);
                        finish();
                        break;

                    case UNAUTHORIZED:
                        Utils.showModelDialog(context, resources.getString(zm.gov.moh.core.R.string.authentication_failed), resources.getString(zm.gov.moh.core.R.string.incorrect_credentials)).show();
                        progressDialog.dismiss();
                        break;

                    case PENDING:
                        progressDialog.show();
                        loginViewModel.getCredentials().clear();
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
        loginViewModel.getRepository().getDatabase().locationDao().getAll().observe(this, this::setLocation);
    }

    public void setLocation(List<Location> locations){

        locationArrayAdapter.clear();
        locationArrayAdapter.addAll(locations);
        locationArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Location location = locationArrayAdapter.getItem(i);
        loginViewModel.saveSessionLocation(location);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}