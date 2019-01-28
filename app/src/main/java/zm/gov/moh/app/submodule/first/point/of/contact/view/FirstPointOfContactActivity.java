package zm.gov.moh.app.submodule.first.point.of.contact.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import zm.gov.moh.app.BR;
import zm.gov.moh.app.R;
import zm.gov.moh.app.databinding.FirstPointOfContactActivityBinding;
import zm.gov.moh.app.submodule.first.point.of.contact.viewmodel.FirstPointOfContactViewModel;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.submodule.Submodule;


public class FirstPointOfContactActivity extends BaseActivity {

    FirstPointOfContactViewModel firstPointOfContactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_point_of_contact_activity);

        firstPointOfContactViewModel = ViewModelProviders.of(this).get(FirstPointOfContactViewModel.class);

      FirstPointOfContactActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.first_point_of_contact_activity);

      binding.setVariable(BR.fpocontactviewmodel, firstPointOfContactViewModel);


        firstPointOfContactViewModel.getStartSubmodule().observe(this, startSubmoduleObserver);

        ToolBarEventHandler toolBarEventHandler = getToolbarHandler();
        toolBarEventHandler.setTitle("Point Of Contact");
        binding.setToolbarhandler(toolBarEventHandler);
        binding.setContext(this);
    }

    final Observer<Submodule> startSubmoduleObserver = this::startSubmodule;
}
