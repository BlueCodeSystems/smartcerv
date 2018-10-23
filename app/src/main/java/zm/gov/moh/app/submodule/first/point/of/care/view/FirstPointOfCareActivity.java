package zm.gov.moh.app.submodule.first.point.of.care.view;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import zm.gov.moh.app.R;
import zm.gov.moh.app.BR;
import zm.gov.moh.app.databinding.FirstPointOfCareActivityBinding;
import zm.gov.moh.app.submodule.first.point.of.care.viewmodel.FirstPointOfCareViewModel;

public class FirstPointOfCareActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirstPointOfCareViewModel firstPointOfCareViewModel = ViewModelProviders.of(this).get(FirstPointOfCareViewModel.class);

       FirstPointOfCareActivityBinding binding =  DataBindingUtil.setContentView(this, R.layout.first_point_of_care_activity);

       binding.setVariable(BR.fpocareviewmodel, firstPointOfCareViewModel);
    }
}
