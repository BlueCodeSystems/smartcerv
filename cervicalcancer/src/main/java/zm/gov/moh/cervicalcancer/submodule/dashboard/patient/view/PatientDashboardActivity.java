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
import zm.gov.moh.common.ui.ToolBarEventHandler;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.utils.BaseApplication;
public class PatientDashboardActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public static final String PERSON_ID = "PERSON_ID";
    public static final String CALLER_SUBMODULE_ID_KEY = "CALLER_SUBMODULE_ID_KEY";
    PatientDashboardViewModel viewModel;
    Module vitals;
    long clientId;
    Client client;
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    Bundle mBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getIntent().getExtras();
        clientId = mBundle.getLong(PERSON_ID);
        viewModel = ViewModelProviders.of(this).get(PatientDashboardViewModel.class);
        viewModel.setBundle(mBundle);
        setViewModel(viewModel);
        AndroidThreeTen.init(this);
        ToolBarEventHandler toolBarEventHandler = getToolbarHandler(this);
        toolBarEventHandler.setTitle("Patient Dashboard");

        vitals = ((BaseApplication)this.getApplication()).getModule(BaseApplication.CoreModule.VITALS);

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
        database.locationDao().getByPatientId(clientId,4L).observe(this ,binding::setFacility);
        database.visitDao().getByPatientIdVisitTypeId(clientId,2L,3L,4L,5L,6L,7L).observe(this,viewModel::onVisitsRetrieved);
        //database.personAttributeTypeDao().findById(11)
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.recents_menu_item_id)
            fragment = new PatientDashboardRecentsViewPagerFragment();
        else if(id == R.id.insights_menu_item_id)
            fragment = new PatientDashboardInsightsViewPagerFragment();
        fragment.setArguments(mBundle);
        replaceFragment(fragment);
        return true;
    }
    public void replaceFragment(Fragment fragment){
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.bottom_navigation_view_container,fragment).commit();
    }
    public Module getVitals() {
        return vitals;
    }
    public Client getClient() {
        return client;
    }
    public PatientDashboardViewModel getViewModel(){
        return viewModel;
    }
}