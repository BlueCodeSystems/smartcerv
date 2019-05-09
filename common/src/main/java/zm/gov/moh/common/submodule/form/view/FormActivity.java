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

    private HashMap<Integer, Uri> uris;
    Bundle mBundle;

    FormViewModel viewModel;
    MutableLiveData<Map.Entry<String, Uri>> activityResultEmitter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        activityResultEmitter = new MutableLiveData<>();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    { //create obs, get concept id, data type, & value
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            uris.put(requestCode, data.getData());
            Bundle bundle = data.getExtras();
            int i = 1;
            i++;

            Map.Entry<String,Uri> result = new AbstractMap.SimpleEntry<>("test",data.getData());
           activityResultEmitter.setValue(result);

        }


    }
}