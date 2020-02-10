package zm.gov.moh.common.submodule.dashboard.client.view;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.format.DateTimeFormatter;

import zm.gov.moh.common.BR;
import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.ActivityClientDashboardBinding;
import zm.gov.moh.common.submodule.dashboard.client.adapter.ClientDashboardFragmentPagerAdapter;
import zm.gov.moh.common.submodule.dashboard.client.viewmodel.ClientDashboardViewModel;
import zm.gov.moh.common.base.BaseEventHandler;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.core.utils.BaseApplication;

public class ClientDashboardActivity extends BaseActivity {

    public static final String PERSON_ID = "PERSON_ID";
    ClientDashboardViewModel viewModel;
    Module vitals;
    long clientId;
    Client client;
    Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidThreeTen.init(this);


        BaseEventHandler toolBarEventHandler = getToolbarHandler(this);

        vitals = ((BaseApplication)this.getApplication()).getModule(BaseApplication.CoreModule.VITALS);

        mBundle = getIntent().getExtras();
        clientId = mBundle.getLong(PERSON_ID);

        viewModel = ViewModelProviders.of(this).get(ClientDashboardViewModel.class);

        ActivityClientDashboardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_client_dashboard);
        binding.setTitle("Client Dashboard");

        initPopupMenu(R.menu.base_menu_edit, toolBarEventHandler::onMenuItemSelected);
        initToolBar(binding.getRoot());

        // Find the view pager that will allow the getUsers to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        ClientDashboardFragmentPagerAdapter adapter = new ClientDashboardFragmentPagerAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewModel.getRepository().getDatabase().clientDao().findById(clientId).observe(this,
                client1 -> {
                    if(client1 != null) {
                        binding.setClient(client1);

                        mBundle.putString(Key.PERSON_FAMILY_NAME, client1.getFamilyName());
                        mBundle.putString(Key.PERSON_GIVEN_NAME, client1.getGivenName());
                        mBundle.putString(Key.PERSON_DOB, client1.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

                        this.getIntent().putExtras(mBundle);
                    }
                    else
                        ClientDashboardActivity.this.onBackPressed();

        });
        viewModel.getRepository().getDatabase().personAddressDao().findByPersonIdObservable(clientId).observe(this, clientAddress ->{

            if(clientAddress != null) {

                binding.setClientAddress(clientAddress);
                mBundle.putString(Key.PERSON_ADDRESS1, clientAddress.getAddress1());

                viewModel.getRepository().getDatabase().locationDao().getLocationByName(clientAddress.getCityVillage())
                        .observe(ClientDashboardActivity.this, location -> {

                            if (location != null) {
                                mBundle.putLong(Key.PERSON_DISTRICT_LOCATION_ID, location.getLocationId());
                                mBundle.putLong(Key.PERSON_PROVINCE_LOCATION_ID, location.getParentLocation());

                                ClientDashboardActivity.this.getIntent().putExtras(mBundle);
                            }
                        });
                this.getIntent().putExtras(mBundle);
            }
        });
        viewModel.getRepository().getDatabase().locationDao().getByPatientId(clientId).observe(this, location -> {
            binding.setVariable(BR.facility, location);
        });
    initBundle(mBundle);
        setViewModel(viewModel);
        addDrawer(this);
    }

    public void bindClient(Client client){

    }

    public Module getVitals() {
        return vitals;
    }

    public long getClientId() {
        return clientId;
    }

    public Client getClient() {
        return client;
    }

    public ClientDashboardViewModel getViewModel(){
        return viewModel;
    }
}