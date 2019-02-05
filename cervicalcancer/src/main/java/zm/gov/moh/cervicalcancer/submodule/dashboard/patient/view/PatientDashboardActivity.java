package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import zm.gov.moh.cervicalcancer.databinding.ActivityPatientDashboardBinding;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel.PatientDashboardViewModel;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.utils.BaseApplication;

public class PatientDashboardActivity extends BaseActivity {

    public static final String PERSON_ID = "PERSON_ID";
    public static final String CALLER_SUBMODULE_ID_KEY = "CALLER_SUBMODULE_ID_KEY";
    PatientDashboardViewModel viewModel;
    Submodule vitals;
    long clientId;
    Client client;

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        clientId = bundle.getLong(PERSON_ID);
        viewModel = ViewModelProviders.of(this).get(PatientDashboardViewModel.class);
        viewModel.setBundle(bundle);
        setViewModel(viewModel);

        AndroidThreeTen.init(this);

        ToolBarEventHandler toolBarEventHandler = getToolbarHandler();
        toolBarEventHandler.setTitle("Patient Dashboard");

        vitals = ((BaseApplication)this.getApplication()).getSubmodule(BaseApplication.CoreModule.VITALS);

        Database database = viewModel.getRepository().getDatabase();



        getViewModel().getRepository().getDatabase().genericDao()
                .getPatientById(clientId)
                .observe(this,patient->{
                    if(patient == null) {
                        Toast.makeText(this, "Client not enrolled", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                });



        ActivityPatientDashboardBinding  binding = DataBindingUtil.setContentView(this, R.layout.activity_patient_dashboard);
        binding.setToolbarhandler(toolBarEventHandler);

        viewModel.getRepository().getDatabase().genericDao().getPatientById(clientId).
                observe(this, binding::setClient);
        viewModel.getRepository().getDatabase().personAddressDao().findByPersonId(clientId).
                observe(this, binding::setClientAddress);

        viewModel.getRepository().getDatabase().locationDao().getByPatientId(clientId).
                observe(this, binding::setFacility);

        // Set Bottom Navigation View Listener
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.inflateMenu(R.menu.bottom_menu);

        fragmentManager = getSupportFragmentManager();
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.register_select) {
                    fragment = new PatientDashboardRecentsViewPagerFragment();
                } else if(id == R.id.visit_select) {
                    fragment = new PatientDashboardVisitViewPagerFragment();
                } else if(id == R.id.vitals_select) {
                    fragment = new PatientDashboardVitalsViewPagerFragment();
                }

                fragment.setArguments(bundle);
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
				transaction.replace(R.id.bottom_navigation_view_container,fragment).commit();
				return true;
            }			
        });
        bottomNavigationView.setSelectedItemId(R.id.register_select);


		database.genericDao().getPatientById(clientId).observe(this, binding::setClient);
        database.personAddressDao().findByPersonId(clientId).observe(this, binding::setClientAddress);
        database.locationDao().getByPatientId(clientId,4L).observe(this ,binding::setFacility);
        database.visitDao().getByPatientId(clientId).observe(this,viewModel::onVisitsRetrieved);

    }
    public Submodule getVitals() {
        return vitals;
    }

    public long getClientId() {
        return clientId;
    }

    public Client getClient() {
        return client;
    }

    public PatientDashboardViewModel getViewModel(){
        return viewModel;
    }
}