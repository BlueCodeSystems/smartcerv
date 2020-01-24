package zm.gov.moh.cervicalcancer.submodule.register.view;

import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.List;

import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import zm.gov.moh.cervicalcancer.OpenmrsConfig;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.databinding.ActivityCervicalCancerRegisterBinding;
import zm.gov.moh.cervicalcancer.submodule.register.adapter.ClientListAdapter;
import zm.gov.moh.cervicalcancer.submodule.register.viewmodel.RegisterViewModel;
import zm.gov.moh.cervicalcancer.submodule.register.viewmodel.StatsViewModel;
import zm.gov.moh.common.ui.BaseRegisterActivity;
import zm.gov.moh.core.model.Key;


public class RegisterActivity extends BaseRegisterActivity<ClientListAdapter> {

    RegisterViewModel registerViewModel;
    StatsViewModel statsViewModel;
    ClientListAdapter mClientListAdapter;
    private static final int NUM_PAGES = 2;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    ActivityCervicalCancerRegisterBinding binding;
    Consumer<Integer> inPatientCountCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         binding = DataBindingUtil.setContentView(this, R.layout.activity_cervical_cancer_register);
        AndroidThreeTen.init(this);

        viewPager = findViewById(R.id.pager);
        //viewPager.setPageTransformer(new DepthPageTransformer());
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);


        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        setViewModel(registerViewModel);

        statsViewModel = ViewModelProviders.of(this).get(StatsViewModel.class);

        ClientListAdapter clientListAdapter;
        clientListAdapter = new ClientListAdapter(this);
        clientListAdapter.setInPatientIdentifierType(4);
        clientListAdapter.setOutPatientIdentifierType(3);
        mClientListAdapter = clientListAdapter;

        registerViewModel.setIdentifierTypeUuid(OpenmrsConfig.IDENTIFIER_TYPE_CCPIZ_UUID);
        registerViewModel.getClientsList().observe(this, clientListAdapter::setClientList);
        registerViewModel.getInPatientCount().observe(this, count -> {

            if(inPatientCountCallback != null)
                inPatientCountCallback.accept(count);
        } );

    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);

        if(binding != null)
            binding.setTitle(title.toString());
    }


    public ClientListAdapter getAdapter() {

        return mClientListAdapter;
    }

    public StatsViewModel getStatsViewModel() {
        return statsViewModel;
    }

    @Override
    public void matchedSearchId(List<Long> ids) {
        registerViewModel.setSearchArguments(ids);
    }

    @Override
    public void getAllClient() {

        //Get all clients if search field is empty
        registerViewModel.getAllClients();
    }

    public void setInPatientCountCallback(Consumer<Integer> inPatientCountCallback){

        this.inPatientCountCallback = inPatientCountCallback;
    }

    public void setCurrentItem(int position) {
         viewPager.setCurrentItem(position, true);
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {

            if(position == 0)
                return new RegisterFragment();
            else
                return new StatisticsFragment();
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}