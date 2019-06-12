package zm.gov.moh.common.submodule.form.view;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.common.base.Strings;

import java.net.URI;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.form.viewmodel.FormViewModel;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.Key;

public class FormActivity extends BaseActivity {

    Bundle mBundle;

    FormViewModel viewModel;
    MutableLiveData<Map.Entry<Integer, Uri>> activityResultEmitter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        activityResultEmitter = new MutableLiveData<>();

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    { //create obs, get concept id, data type, & value
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Map.Entry<Integer,Uri> result = new AbstractMap.SimpleEntry<>(requestCode,data.getData());
            activityResultEmitter.setValue(result);
        }


    }
}