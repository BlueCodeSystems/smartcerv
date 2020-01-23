package zm.gov.moh.cervicalcancer.submodule.register.view;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntent().putExtra(Key.PERSON_ID,1234L);

         binding = DataBindingUtil.setContentView(this, R.layout.activity_cervical_cancer_register);
        AndroidThreeTen.init(this);

        viewPager = findViewById(R.id.pager);
        viewPager.setPageTransformer(new DepthPageTransformer());
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

    //Transition
    @RequiresApi(21)
    public class DepthPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setTranslationZ(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);
                // Move it behind the left page
                view.setTranslationZ(-1f);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }
}