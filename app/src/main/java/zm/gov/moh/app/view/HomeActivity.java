package zm.gov.moh.app.view;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import zm.gov.moh.app.R;
import zm.gov.moh.app.databinding.FirstPointOfContactActivityBinding;
import zm.gov.moh.app.viewmodel.HomeViewModel;
import zm.gov.moh.cervicalcancer.view.CervicalCancerHomeFragment;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.common.view.CommonHomeFragment;
import zm.gov.moh.core.model.submodule.Module;


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
                .getLong(this.getResources().getString(zm.gov.moh.core.R.string.session_location_key), 1);



        metrics = new HashMap<>();

      FirstPointOfContactActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.first_point_of_contact_activity);


        homeViewModel.getStartSubmodule().observe(this, startSubmoduleObserver);

        ToolBarEventHandler toolBarEventHandler = getToolbarHandler();
        toolBarEventHandler.setTitle("Home");
        binding.setToolbarhandler(toolBarEventHandler);
        binding.setContext(this);

        Fragment common = new CommonHomeFragment();

        Fragment cervicalCancer = new CervicalCancerHomeFragment();

        FragmentManager fragmentTransitionSupport = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentTransitionSupport.beginTransaction();



    }

    @Override
    public void init() {
        
    }

    @Override
    public void onClick(View view) {

    }

    final Observer<Module> startSubmoduleObserver = this::startModule;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
