package zm.gov.moh.cervicalcancer.submodule.cervicalcancer.view;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.BR;
import zm.gov.moh.cervicalcancer.databinding.CervicalCancerActivityBinding;
import zm.gov.moh.cervicalcancer.submodule.cervicalcancer.viewmodel.CervicalCancerViewModel;
import zm.gov.moh.common.ui.BaseActivity;

public class CervicalCancerActivity extends BaseActivity {

    CervicalCancerViewModel cervicalCancerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cervicalCancerViewModel = ViewModelProviders.of(this).get(CervicalCancerViewModel.class);

        CervicalCancerActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.cervical_cancer_activity);

        binding.setVariable(BR.ccancerviewmodel, cervicalCancerViewModel);

        cervicalCancerViewModel.getStartSubmodule().observe(this,this::startModule);

        ToolBarEventHandler toolBarEventHandler = getToolbarHandler();
        toolBarEventHandler.setTitle("Cervical Cancer");
        binding.setToolbarhandler(toolBarEventHandler);
        binding.setContext(this);
    }

    @Override
    public void init() {

    }

    @Override
    public void onClick(View view) {

    }
}
