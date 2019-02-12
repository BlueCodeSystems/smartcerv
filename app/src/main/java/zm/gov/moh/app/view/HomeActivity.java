package zm.gov.moh.app.view;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;
import java.util.HashMap;
import java.util.Map;

import zm.gov.moh.app.R;
import zm.gov.moh.app.databinding.FirstPointOfContactActivityBinding;
import zm.gov.moh.app.viewmodel.FirstPointOfContactViewModel;
import zm.gov.moh.cervicalcancer.view.CervicalCancerHomeFragment;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.common.view.CommonHomeFragment;
import zm.gov.moh.core.model.submodule.Submodule;


public class HomeActivity extends BaseActivity implements CommonHomeFragment.OnFragmentInteractionListener {

    FirstPointOfContactViewModel firstPointOfContactViewModel;
    Map<String,Long> metrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_point_of_contact_activity);

        firstPointOfContactViewModel = ViewModelProviders.of(this).get(FirstPointOfContactViewModel.class);
        setViewModel(firstPointOfContactViewModel);

        final long SESSION_LOCATION_ID = this.getViewModel().getRepository().getDefaultSharePrefrences()
                .getLong(this.getResources().getString(zm.gov.moh.core.R.string.session_location_key), 1);



        metrics = new HashMap<>();

      FirstPointOfContactActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.first_point_of_contact_activity);


        firstPointOfContactViewModel.getStartSubmodule().observe(this, startSubmoduleObserver);

        ToolBarEventHandler toolBarEventHandler = getToolbarHandler();
        toolBarEventHandler.setTitle("Home");
        binding.setToolbarhandler(toolBarEventHandler);
        binding.setContext(this);

        Fragment common = new CommonHomeFragment();

        Fragment cervicalCancer = new CervicalCancerHomeFragment();

        FragmentManager fragmentTransitionSupport = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentTransitionSupport.beginTransaction();



    }

    final Observer<Submodule> startSubmoduleObserver = this::startSubmodule;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
