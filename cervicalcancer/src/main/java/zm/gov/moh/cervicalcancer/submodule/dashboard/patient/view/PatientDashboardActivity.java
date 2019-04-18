package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jakewharton.threetenabp.AndroidThreeTen;
import zm.gov.moh.cervicalcancer.databinding.ActivityPatientDashboardBinding;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel.PatientDashboardViewModel;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.utils.BaseApplication;

import static zm.gov.moh.cervicalcancer.R.id.load_image;

public class PatientDashboardActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public static final String PERSON_ID = "PERSON_ID";
    public static final String CALLER_SUBMODULE_ID_KEY = "CALLER_SUBMODULE_ID_KEY";
    PatientDashboardViewModel viewModel;
    Module vitals;
    long clientId;
    Client client;
    private Button LoadImageButton;
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    Bundle mBundle;
    private View v;
    private ImageView img1, img2;
    private final int CODE_IMG_GALLERY = 1;
    private final int CODE_MULTIPLE_IMG_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getIntent().getExtras();
        clientId = mBundle.getLong(PERSON_ID);
        viewModel = ViewModelProviders.of(this).get(PatientDashboardViewModel.class);
        viewModel.setBundle(mBundle);
        setViewModel(viewModel);
        AndroidThreeTen.init(this);
        ToolBarEventHandler toolBarEventHandler = getToolbarHandler();
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
        viewModel.getRepository().getDatabase().personAddressDao().findByPersonId(clientId).
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
        database.personAddressDao().findByPersonId(clientId).observe(this, binding::setClientAddress);
        database.locationDao().getByPatientId(clientId,4L).observe(this ,binding::setFacility);
        database.visitDao().getByPatientIdVisitTypeId(clientId,2L,3L,4L,5L,6L,7L).observe(this,viewModel::onVisitsRetrieved);
    }

    @Override
    public void init() {

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





    @Override
    public void onClick(View view) {

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

    public void loadImageMethod(View view) {
        img1.setOnClickListener((View v) -> {
                    loadImageMethod(v);
                    startActivityForResult(Intent.createChooser(new Intent().setAction(Intent.ACTION_GET_CONTENT).setType("image/*"),
                            "Digital Cervicography"), CODE_IMG_GALLERY);
                });
        img2.setOnClickListener((View v) -> {
        loadImageMethod(v);
        //MULTIPLE IMGS
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Digital Cervicography"),
                CODE_MULTIPLE_IMG_GALLERY);
    }
        );
}
    }
