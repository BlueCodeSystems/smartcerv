package zm.gov.moh.app.submodule.modulesdrawer.view;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import zm.gov.moh.app.R;
import zm.gov.moh.app.BR;
import zm.gov.moh.app.databinding.ActivityModulesBinding;
import zm.gov.moh.app.submodule.modulesdrawer.viewmodel.ModulesDrawerViewModel;
import zm.gov.moh.common.component.login.view.LoginActivity;
import zm.gov.moh.common.component.login.viewmodel.LoginViewModel;

public class ModulesDrawerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ModulesDrawerViewModel modulesDrawerViewModel = ViewModelProviders.of(this).get(ModulesDrawerViewModel.class);

        ActivityModulesBinding binding =  DataBindingUtil.setContentView(this, R.layout.activity_modules);

        binding.setVariable(BR.modulesdrawerviewmodel, modulesDrawerViewModel);
    }

}
