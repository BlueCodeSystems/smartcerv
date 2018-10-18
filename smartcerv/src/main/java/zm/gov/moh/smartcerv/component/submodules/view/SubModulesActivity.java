package zm.gov.moh.smartcerv.component.submodules.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import zm.gov.moh.smartcerv.BR;
import zm.gov.moh.smartcerv.R;
import zm.gov.moh.smartcerv.component.submodules.viewmodel.SubModulesViewModel;
import zm.gov.moh.smartcerv.databinding.SubmodulesActivityBinding;

public class SubModulesActivity extends AppCompatActivity {

    SubModulesViewModel subModulesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submodules_activity);

        subModulesViewModel = ViewModelProviders.of(this).get(SubModulesViewModel.class);

       SubmodulesActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.submodules_activity);

       binding.setVariable(BR.subviewmodel, subModulesViewModel);
    }
}
