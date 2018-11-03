package zm.gov.moh.common.submodule.dashboard.client.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.jakewharton.threetenabp.AndroidThreeTen;

import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.ActivityClientDashboardBinding;
import zm.gov.moh.common.submodule.dashboard.client.adapter.ClientDashboardFragmentPagerAdapter;
import zm.gov.moh.common.submodule.dashboard.client.viewmodel.ClientDashboardViewModel;
import zm.gov.moh.core.utils.BaseActivity;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.model.submodule.Submodule;

public class ClientDashboardActivity extends BaseActivity {

    public static final String CLIENT_ID_KEY = "CLIENT_ID_KEY";
    public static final String CALLER_SUBMODULE_ID_KEY = "CALLER_SUBMODULE_ID_KEY";
    ClientDashboardViewModel mClientDashboardViewModel;
    Submodule vitals;
    long clientId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidThreeTen.init(this);

        vitals = ((BaseApplication)this.getApplication()).getSubmodule(BaseApplication.CoreSubmodules.VITALS);

        clientId = getIntent().getExtras().getLong(CLIENT_ID_KEY);

        mClientDashboardViewModel = ViewModelProviders.of(this).get(ClientDashboardViewModel.class);

        ActivityClientDashboardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_client_dashboard);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        ClientDashboardFragmentPagerAdapter adapter = new ClientDashboardFragmentPagerAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        mClientDashboardViewModel.getClientById(clientId).observe(this, binding::setClient);

        mClientDashboardViewModel.getPersonAddressByPersonId(clientId).observe(this, binding::setClientAddress);
    }

    public Submodule getVitals() {
        return vitals;
    }

    public long getClientId() {
        return clientId;
    }
}