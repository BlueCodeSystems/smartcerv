package zm.gov.moh.common.submodule.dashboard.client.view;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;

import com.jakewharton.threetenabp.AndroidThreeTen;

import zm.gov.moh.common.BR;
import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.ActivityClientDashboardBinding;
import zm.gov.moh.common.submodule.dashboard.client.adapter.ClientDashboardFragmentPagerAdapter;
import zm.gov.moh.common.submodule.dashboard.client.viewmodel.ClientDashboardViewModel;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.utils.BaseApplication;

public class ClientDashboardActivity extends BaseActivity {

    public static final String PERSON_ID = "PERSON_ID";
    public static final String CALLER_SUBMODULE_ID_KEY = "CALLER_SUBMODULE_ID_KEY";
    ClientDashboardViewModel viewModel;
    Module vitals;
    long clientId;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidThreeTen.init(this);

        ToolBarEventHandler toolBarEventHandler = getToolbarHandler();
        toolBarEventHandler.setTitle("Client Dashboard");

        vitals = ((BaseApplication)this.getApplication()).getSubmodule(BaseApplication.CoreModule.VITALS);

        clientId = getIntent().getExtras().getLong(PERSON_ID);

        viewModel = ViewModelProviders.of(this).get(ClientDashboardViewModel.class);

        ActivityClientDashboardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_client_dashboard);
        binding.setToolbarhandler(toolBarEventHandler);

        // Find the view pager that will allow the getUsers to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        ClientDashboardFragmentPagerAdapter adapter = new ClientDashboardFragmentPagerAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewModel.getRepository().getDatabase().clientDao().findById(clientId).observe(this, binding::setClient);
        viewModel.getRepository().getDatabase().personAddressDao().findByPersonId(clientId).observe(this, binding::setClientAddress);
        viewModel.getRepository().getDatabase().locationDao().getByPatientId(clientId).observe(this, location -> {
            binding.setVariable(BR.facility, location);
        });
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