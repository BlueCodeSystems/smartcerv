package zm.gov.moh.common.submodule.form.view;

import android.os.Bundle;

import zm.gov.moh.common.R;
import zm.gov.moh.core.utils.BaseActivity;

public class FormActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        FormFragment formFragment = new FormFragment();
        formFragment.setArguments(getIntent().getExtras());

        setFragment(formFragment);
    }
}
