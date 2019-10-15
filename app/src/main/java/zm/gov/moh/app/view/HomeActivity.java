package zm.gov.moh.app.view;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import java.util.HashMap;
import java.util.Map;

import zm.gov.moh.app.R;
import zm.gov.moh.app.databinding.FirstPointOfContactActivityBinding;
import zm.gov.moh.app.viewmodel.HomeViewModel;
import zm.gov.moh.cervicalcancer.view.CervicalCancerHomeFragment;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.common.base.BaseEventHandler;
import zm.gov.moh.common.view.CommonHomeFragment;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.service.SearchIndex;


public class HomeActivity extends BaseActivity implements CommonHomeFragment.OnFragmentInteractionListener {

    HomeViewModel homeViewModel;
    Map<String,Long> metrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_point_of_contact_activity);

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        setViewModel(homeViewModel);

        final long SESSION_LOCATION_ID = this.getViewModel().getRepository().getDefaultSharePrefrences()
                .getLong(Key.LOCATION_ID, 1);



        metrics = new HashMap<>();

        FirstPointOfContactActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.first_point_of_contact_activity);


        homeViewModel.getStartSubmodule().observe(this, startSubmoduleObserver);

        BaseEventHandler toolBarEventHandler = getToolbarHandler(this);
        toolBarEventHandler.setTitle("Home");
        binding.setToolbarhandler(toolBarEventHandler);
        binding.setContext(this);
        addDrawer(this);
        Fragment common = new CommonHomeFragment();

        Fragment cervicalCancer = new CervicalCancerHomeFragment();

        FragmentManager fragmentTransitionSupport = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentTransitionSupport.beginTransaction();

        startService(new Intent(this,SearchIndex.class));

    }

    final Observer<Module> startSubmoduleObserver = this::startModule;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}