package zm.gov.moh.common.submodule.settings.view;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.ActivitySettingsBinding;
import zm.gov.moh.common.submodule.settings.model.Preferences;
import zm.gov.moh.common.submodule.settings.viewmodel.SettingsViewModel;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class Settings extends BaseActivity {



    SettingsViewModel viewModel;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        binding.setViewmodel(viewModel);
        binding.setPreferences(new Preferences(viewModel.getRepository()));

        viewModel.getPreferencesSaveStatusStream().observe(this, status -> {if(status)onPreferencesSaved();} );

        viewModel.getProgressIndicatorVisibilityStatusStream().observe(this, this:: showProgressDialog);

        viewModel.getPromptSaveDialogVisibilityStream().observe(this,  this::showDialog);

        viewModel.getPreferencesStream().observe(this,  pref -> this.preferences = pref );
    }

    public void onPreferencesSaved(){
        finish();
        startModule(BaseApplication.CoreModule.BOOTSTRAP);
    }

    public void showProgressDialog(boolean visibility){

        if(visibility) {
            if(progressDialog == null)
                progressDialog = Utils.showProgressDialog(this, getResources().getString(R.string.please_wait));
            progressDialog.show();
        }
        else
            if(progressDialog != null)
                progressDialog.dismiss();
    }

    public void showDialog(boolean visibility){


        alertDialog = new AlertDialog.Builder(this)
                .setTitle("Do your want proceed")
                .setMessage("Some data has not been synchronized. Please synchronize before proceeding otherwise the data will be lost. ")
                .setPositiveButton("proceed", (DialogInterface dialogInterface, int i) -> {

                          preferences.setPromptToSave(false);
                          if(preferences != null)
                            viewModel.saveBaseUrl(preferences);
                    }
                )
                .setNegativeButton("cancel",(DialogInterface dialogInterface, int i) -> onBackPressed())
                .show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));

    }
}
