package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jakewharton.threetenabp.AndroidThreeTen;
import java.io.IOException;
import zm.gov.moh.cervicalcancer.databinding.ActivityPatientDashboardBinding;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel.PatientDashboardViewModel;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.view.MyDialogFragment;
import zm.gov.moh.common.model.VisitMetadata;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.common.base.BaseEventHandler;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;


public class PatientDashboardActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public static final String PERSON_ID = "PERSON_ID";
    public static final String CALLER_SUBMODULE_ID_KEY = "CALLER_SUBMODULE_ID_KEY";
    PatientDashboardViewModel viewModel;
    Module vitals;
    long clientId;
    Client client;
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private Activity activity;
    private FragmentManager fragmentManager;
    Bundle mBundle;
    private View ImageButton1;
    private View ImageButton2;
    private MenuItem item;
    private View imageView;
    private Object context;
    private ImageView imageView2;
    private View v;
    private Object Tag;
    private Object mContext;
    private Object Application;
    private android.app.Application application;
    private Object VisitState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getIntent().getExtras();
        clientId = mBundle.getLong(PERSON_ID);
        viewModel = ViewModelProviders.of(this).get(PatientDashboardViewModel.class);
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
        ActivityPatientDashboardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_patient_dashboard);
        binding.setTitle("Patient Dashboard");

        initToolBar(binding.getRoot());
        viewModel.getRepository().getDatabase().genericDao().getPatientById(clientId).
                observe(this, binding::setClient);
        viewModel.getRepository().getDatabase().personAddressDao().findByPersonIdObservable(clientId).
                observe(this, binding::setClientAddress);
        viewModel.getRepository().getDatabase().locationDao().getByPatientId(clientId).
                observe(this, binding::setFacility);

        //Set EDI Image View Listener
        ImageButton1 = findViewById(R.id.load_image);





        // Set Bottom Navigation View Listener
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.inflateMenu(R.menu.bottom_menu);
        fragmentManager = getSupportFragmentManager();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.recents_menu_item_id);
        database.genericDao().getPatientById(clientId).observe(this, binding::setClient);
        database.personAddressDao().findByPersonIdObservable(clientId).observe(this, binding::setClientAddress);
        database.locationDao().getByPatientId(clientId, 4L).observe(this, binding::setFacility);
        database.visitDao().getByPatientIdVisitTypeId(clientId, 2L, 3L, 4L, 5L, 6L, 7L).observe(this, viewModel::onVisitsRetrieved);

        //set navigation drawer
        addDrawer(this);

    }










    public void EDIonClick(final View v) {
        /*if (VisitState == null) {
            Toast.makeText(this, "No Images Loaded", Toast.LENGTH_LONG);
        } else */
        fragment = new PatientDashboardEDIGalleryFragment();
        fragment.setArguments(mBundle);
        replaceFragment(fragment);
        System.out.println("clicked");

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.recents_menu_item_id)
            fragment = new PatientDashboardRecentsViewPagerFragment();
        else if (id == R.id.insights_menu_item_id)
            fragment = new PatientDashboardInsightsViewPagerFragment();
        fragment.setArguments(mBundle);
        replaceFragment(fragment);
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.bottom_navigation_view_container, fragment).commit();
    }

    public Module getVitals() {
        return vitals;
    }

    public Client getClient() {
        return client;
    }

    public PatientDashboardViewModel getViewModel() {
        return viewModel;
    }


    public void startVisit() {

        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        MyDialogFragment dialogFragment = new MyDialogFragment();

        dialogFragment.show(fragmentManager, "Test");
    }

    public void selectVisit(int visit) throws IOException {

        switch (visit) {

            case 1:

                VisitMetadata visitMetadata = null;
                try {
                    visitMetadata = new VisitMetadata(this, Utils.getStringFromInputStream(this.getAssets().open("visits/via.json")));
                    mBundle.putSerializable(Key.VISIT_METADATA, visitMetadata);
                    mBundle.putSerializable(Key.VISIT_STATE, zm.gov.moh.core.model.VisitState.NEW);
                    this.startModule(BaseApplication.CoreModule.VISIT, mBundle);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 2:


                    visitMetadata = new VisitMetadata(this, Utils.getStringFromInputStream(this.getAssets().open("visits/leep.json")));
                    mBundle.putSerializable(Key.VISIT_METADATA, visitMetadata);
                    mBundle.putSerializable(Key.VISIT_STATE, zm.gov.moh.core.model.VisitState.NEW);
                    this.startModule(BaseApplication.CoreModule.VISIT, mBundle);

                }
        }
    }

                        /*mBundle.putSerializable(Key.VISIT_METADATA, visitMetadata);
                        mBundle.putSerializable(Key.VISIT_STATE, zm.gov.moh.core.model.VisitState.NEW);
                        this.startModule(BaseApplication.CoreModule.VISIT, mBundle);
                } catch (IOException e) {
                }*/


        /*try {
            VisitMetadata visitMetadata = new VisitMetadata(this, Utils.getStringFromInputStream(this.getAssets().open("visits/via.json")));
            mBundle.putSerializable(Key.VISIT_METADATA, visitMetadata);
            mBundle.putSerializable(Key.VISIT_STATE, zm.gov.moh.core.model.VisitState.NEW);
            this.startModule(BaseApplication.CoreModule.VISIT, mBundle);
        } catch (Exception e) {

            try {
                VisitMetadata visitMetadata = new VisitMetadata(this, Utils.getStringFromInputStream(this.getAssets().open("visits/leep.json")));
                mBundle.putSerializable(Key.VISIT_METADATA, visitMetadata);
                mBundle.putSerializable(Key.VISIT_STATE, zm.gov.moh.core.model.VisitState.NEW);
                this.startModule(BaseApplication.CoreModule.VISIT, mBundle);
            } catch (Exception e) {*/




