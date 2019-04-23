package zm.gov.moh.common.submodule.form.view;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.HashMap;

import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.form.viewmodel.FormViewModel;
import zm.gov.moh.common.ui.BaseActivity;

public class FormActivity extends BaseActivity {

    private HashMap<Integer, Uri> uris;
    Bundle mBundle;

    FormViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        uris = new HashMap<>();

        if(mBundle == null)
            mBundle = new Bundle();

        viewModel = ViewModelProviders.of(this).get(FormViewModel.class);

        FormFragment formFragment = new FormFragment();
        formFragment.setArguments(getIntent().getExtras());

        setTheme(R.style.Smartcerv);
        setFragment(formFragment);


    }

    public FormViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uris.put(requestCode, data.getData());
        Bundle bundle = this.getIntent().getExtras();
        bundle.putSerializable("URI",uris);
        int i = 1;
        i++;


    }
}
