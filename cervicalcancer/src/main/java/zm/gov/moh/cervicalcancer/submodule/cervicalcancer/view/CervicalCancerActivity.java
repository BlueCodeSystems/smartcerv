package zm.gov.moh.cervicalcancer.submodule.cervicalcancer.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.BR;
import zm.gov.moh.cervicalcancer.databinding.CervicalCancerActivityBinding;
import zm.gov.moh.cervicalcancer.submodule.cervicalcancer.viewmodel.CervicalCancerViewModel;

public class CervicalCancerActivity extends AppCompatActivity {

    CervicalCancerViewModel cervicalCancerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cervicalCancerViewModel = ViewModelProviders.of(this).get(CervicalCancerViewModel.class);

        CervicalCancerActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.cervical_cancer_activity);

        binding.setVariable(BR.ccancerviewmodel, cervicalCancerViewModel);
    }
}
