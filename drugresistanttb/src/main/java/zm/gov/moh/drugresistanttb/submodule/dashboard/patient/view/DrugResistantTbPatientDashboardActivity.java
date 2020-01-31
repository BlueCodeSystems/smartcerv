package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.view;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.format.DateTimeFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.common.model.JsonForm;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.submodule.BasicModule;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;
import zm.gov.moh.drugresistanttb.R;
import zm.gov.moh.drugresistanttb.databinding.ActivityDrugResistantTbPatientDashboardBinding;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.adapter.MdrFormListAdapter;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.model.FormGroup;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.viewmodel.DrugResistantTbPatientDashboardViewModel;

public class DrugResistantTbPatientDashboardActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final String PERSON_ID = "PERSON_ID";

    DrugResistantTbPatientDashboardViewModel viewModel;
    Bundle mBundle;
    Module vitals;
    long clientId;
    Client client;
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    List<FormGroup> mdrFormLists = new ArrayList<>();
    private Bundle bundle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mBundle = getIntent().getExtras();
        clientId = mBundle.getLong(PERSON_ID);
        viewModel = ViewModelProviders.of(this).get(DrugResistantTbPatientDashboardViewModel.class);

        viewModel.setBundle(mBundle);
        setViewModel(viewModel);
        AndroidThreeTen.init(this);

        vitals = ((BaseApplication) this.getApplication()).getModule(BaseApplication.CoreModule.VITALS);
        Database database = viewModel.getRepository().getDatabase();

        ActivityDrugResistantTbPatientDashboardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_drug_resistant_tb_patient_dashboard);
        binding.setTitle("MDR Patient Dashboard");

        FormGroup formGroup = new FormGroup();
        List<BasicModule> list = new ArrayList<>();
        BasicModule formModule;

        formModule = new BasicModule("MDR Visits", DrugResistantTbMonthlyReviewFormActivity.class);

        list.add(formModule);

        formGroup.setTitle("MDR Forms");
        formGroup.setFormList(list);
        mdrFormLists.add(formGroup);

        // Create an adapter that knows which fragment should be shown on each page
        MdrFormListAdapter adapter = new MdrFormListAdapter(this, mdrFormLists, mBundle);
        ExpandableListView formListView = findViewById(R.id.mdr_adapter_list);
        formListView.setAdapter(adapter);


        getViewModel().getRepository().getDatabase().genericDao().getMdrPatientById(clientId)
            .observe(this, patient -> {
                if (patient == null) {
                    Toast.makeText(this, "Client not enrolled", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
        });

        initToolBar(binding.getRoot());
        /*viewModel.getRepository().getDatabase().genericDao().getMdrPatientById(clientId).
                observe(this, binding::setClient);
        viewModel.getRepository().getDatabase().personAddressDao().findByPersonIdObservable(clientId).
                observe(this, binding::setClientAddress);
        viewModel.getRepository().getDatabase().locationDao().getByPatientId(clientId).
                observe(this, binding::setFacility);*/

        // adding patient information in databundle
        viewModel.getRepository().getDatabase().clientDao().findById(clientId)
                .observe(this, client1 -> {
                    //binding.setClient(client1);

                    mBundle.putString(Key.PERSON_FAMILY_NAME, client1.getFamilyName());
                    mBundle.putString(Key.PERSON_GIVEN_NAME, client1.getGivenName());
                    mBundle.putString(Key.PERSON_DOB, client1.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    this.getIntent().putExtras(mBundle);
                });

        initBundle(mBundle);

        // Set Bottom Navigation View Listener
        bottomNavigationView = findViewById(R.id.bottom_navigation_view1);

        bottomNavigationView.inflateMenu(R.menu.bottom_menu);
        fragmentManager = getSupportFragmentManager();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.recents_menu_item_id);

        database.genericDao().getMdrPatientById(clientId).observe(this, binding::setClient);
        database.personAddressDao().findByPersonIdObservable(clientId).observe(this, binding::setClientAddress);
        database.locationDao().getByPatientId(clientId, 7L).observe(this, binding::setFacility);
        //database.visitDao().getByPatientIdVisitTypeId(clientId, 2L, 3L, 4L, 5L, 6L, 7L).observe(this, viewModel::onVisitsRetrieved);

        //set navigation drawer
        addDrawer(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }


    public Module getVitals() {
        return vitals;
    }

    public Client getClient() {
        return client;
    }

    public DrugResistantTbPatientDashboardViewModel getViewModel() {
        return viewModel;
    }
}





