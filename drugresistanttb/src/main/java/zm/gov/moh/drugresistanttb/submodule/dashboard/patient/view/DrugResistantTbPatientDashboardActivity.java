package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.view;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jakewharton.threetenabp.AndroidThreeTen;

import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.drugresistanttb.R;
import zm.gov.moh.drugresistanttb.databinding.ActivityDrugResistantTbPatientDashboardBinding;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.viewmodel.DrugResistantTbPatientDashboardViewModel;

public class DrugResistantTbPatientDashboardActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final String PERSON_ID = "PERSON_ID";

    DrugResistantTbPatientDashboardViewModel viewModel;
    Bundle mBundle;
    Module vitals;
    long clientId;
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mBundle = getIntent().getExtras();
        clientId = mBundle.getLong(PERSON_ID);

        ActivityDrugResistantTbPatientDashboardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_drug_resistant_tb_patient_dashboard);
        binding.setTitle("MDR Patient Dashboard");

        try {
            viewModel = ViewModelProviders.of(this).get(DrugResistantTbPatientDashboardViewModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewModel.setBundle(mBundle);
        setViewModel(viewModel);
        AndroidThreeTen.init(this);

        vitals = ((BaseApplication) this.getApplication()).getModule(BaseApplication.CoreModule.VITALS);
        Database database = viewModel.getRepository().getDatabase();
        getViewModel().getRepository().getDatabase().genericDao()
                .getPatientById(clientId)
                .observe(this, patient -> {
                    if (patient == null) {
                        Toast.makeText(this, "Client not enrolled", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                });

        initToolBar(binding.getRoot());
        viewModel.getRepository().getDatabase().genericDao().getPatientById(clientId).
                observe(this, binding::setClient);
        viewModel.getRepository().getDatabase().personAddressDao().findByPersonIdObservable(clientId).
                observe(this, binding::setClientAddress);
        viewModel.getRepository().getDatabase().locationDao().getByPatientId(clientId).
                observe(this, binding::setFacility);

        // Set Bottom Navigation View Listener
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.inflateMenu(R.menu.bottom_menu);
        fragmentManager = getSupportFragmentManager();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.recents_menu_item_id);

        database.genericDao().getPatientById(clientId).observe(this, binding::setClient);
        database.personAddressDao().findByPersonIdObservable(clientId).observe(this, binding::setClientAddress);
        database.locationDao().getByPatientId(clientId, 4L).observe(this, binding::setFacility);
        //database.visitDao().getByPatientIdVisitTypeId(clientId, 2L, 3L, 4L, 5L, 6L, 7L).observe(this, viewModel::onVisitsRetrieved);

        database.locationDao().getByPatientId(clientId, 4L).observe(this, binding::setFacility);
        //database.visitDao().getByPatientIdVisitTypeId(clientId, 2L, 3L, 4L, 5L, 6L, 7L).observe(this, viewModel::onVisitsRetrieved);

        //set navigation drawer
        addDrawer(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}




