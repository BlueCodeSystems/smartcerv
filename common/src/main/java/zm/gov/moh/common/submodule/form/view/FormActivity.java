package zm.gov.moh.common.submodule.form.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.dashboard.client.viewmodel.ClientDashboardViewModel;
import zm.gov.moh.common.submodule.form.viewmodel.FormViewModel;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.utils.BaseActivity;

public class FormActivity extends BaseActivity {

    FormViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        viewModel = ViewModelProviders.of(this).get(FormViewModel.class);

        FormFragment formFragment = new FormFragment();
        formFragment.setArguments(getIntent().getExtras());

        setFragment(formFragment);


    }

    public FormViewModel getViewModel() {
        return viewModel;
    }
}
