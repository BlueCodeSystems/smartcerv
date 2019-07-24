package zm.gov.moh.common.submodule.settings.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.ActivitySettingsBinding;
import zm.gov.moh.common.submodule.settings.model.Preferences;
import zm.gov.moh.common.submodule.settings.viewmodel.SettingsViewModel;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.utils.BaseApplication;

import android.os.Bundle;

public class Settings extends BaseActivity {


    SettingsViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        binding.setViewmodel(viewModel);
        binding.setPreferences(new Preferences(viewModel.getRepository()));

        viewModel.getPreferencesSaveStatus().observe(this,status -> {if(status)onPreferencesSaved();} );
    }

    public void onPreferencesSaved(){
        startModule(BaseApplication.CoreModule.BOOTSTRAP);
    }
}
