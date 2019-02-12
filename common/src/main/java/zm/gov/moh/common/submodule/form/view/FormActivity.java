package zm.gov.moh.common.submodule.form.view;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;

import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.form.viewmodel.FormViewModel;
import zm.gov.moh.common.ui.BaseActivity;

public class FormActivity extends BaseActivity {

    FormViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        viewModel = ViewModelProviders.of(this).get(FormViewModel.class);

        FormFragment formFragment = new FormFragment();
        formFragment.setArguments(getIntent().getExtras());

        setTheme(R.style.Smartcerv);
        setFragment(formFragment);


    }

    public FormViewModel getViewModel() {
        return viewModel;
    }
}
