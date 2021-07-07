package zm.gov.moh.cervicalcancer.submodule.cervicalcancer.view;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;

import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.BR;
import zm.gov.moh.cervicalcancer.databinding.CervicalCancerActivityBinding;
import zm.gov.moh.cervicalcancer.submodule.register.viewmodel.StatsViewModel;
import zm.gov.moh.cervicalcancer.submodule.register.view.StatisticsFragment;
import zm.gov.moh.common.base.BaseActivity;

public class CervicalCancerActivity extends BaseActivity {

    StatsViewModel cervicalCancerViewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle mbundle = new Bundle();
        cervicalCancerViewModel = ViewModelProviders.of(this).get(StatsViewModel.class);

        setViewModel(cervicalCancerViewModel);
        mbundle.putLong("identifier", 4);


        CervicalCancerActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.cervical_cancer_activity);

        binding.setVariable(BR.ccancerviewmodel, cervicalCancerViewModel);

        cervicalCancerViewModel.getStartSubmodule().observe(this, this::startModule);

        initPopupMenu(R.menu.base_menu, toolBarEventHandler::onMenuItemSelected);
        initToolBar(binding.getRoot());

        binding.setTitle("Cervical Cancer");
        binding.setContext(this);
        binding.setBundle(mbundle);
        addDrawer(this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.your_placeholder, new StatisticsFragment());
        ft.commit();


    }

}
