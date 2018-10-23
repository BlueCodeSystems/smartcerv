package zm.gov.moh.app.submodule.first.point.of.contact.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import zm.gov.moh.app.BR;
import zm.gov.moh.app.R;
import zm.gov.moh.app.databinding.FirstPointOfContactActivityBinding;
import zm.gov.moh.app.submodule.first.point.of.contact.viewmodel.FirstPointOfContactViewModel;


public class FirstPointOfContactActivity extends AppCompatActivity {

    FirstPointOfContactViewModel firstPointOfContactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_point_of_contact_activity);

        firstPointOfContactViewModel = ViewModelProviders.of(this).get(FirstPointOfContactViewModel.class);

      FirstPointOfContactActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.first_point_of_contact_activity);

      binding.setVariable(BR.fpocontactviewmodel, firstPointOfContactViewModel);
    }
}
