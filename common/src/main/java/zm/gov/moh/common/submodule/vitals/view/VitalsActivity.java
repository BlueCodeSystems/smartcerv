package zm.gov.moh.common.submodule.vitals.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import zm.gov.moh.common.R;
import zm.gov.moh.common.BR;
import zm.gov.moh.common.databinding.ActivityVitalsBinding;
import zm.gov.moh.common.submodule.dashboard.client.view.ClientDashboardActivity;
import zm.gov.moh.common.submodule.register.view.RegisterActivity;
import zm.gov.moh.common.submodule.vitals.viewmodel.VitalsViewModel;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.model.submodule.Submodule;

public class VitalsActivity extends BaseActivity {

    Bundle bundle;
    Submodule register;
    Submodule callerSubmodule;
    VitalsViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getIntent().getExtras();

        viewModel = ViewModelProviders.of(this).get(VitalsViewModel.class);

        register = ((BaseApplication)this.getApplication()).getSubmodule(BaseApplication.CoreSubmodules.REGISTER);

        long clientId = 0;

        if(bundle != null) {

            try {

                clientId = bundle.getLong(ClientDashboardActivity.CLIENT_ID_KEY);
            } catch (Exception e) {

                bundle = new Bundle();
                callerSubmodule = ((BaseApplication)this.getApplication()).getSubmodule(BaseApplication.CoreSubmodules.VITALS);
                bundle.putSerializable(RegisterActivity.START_SUBMODULE_WITH_RESULT_KEY, callerSubmodule);
                startSubmodule(register,bundle);
                finish();
            }
        }else{

            bundle = new Bundle();
            callerSubmodule = ((BaseApplication)this.getApplication()).getSubmodule(BaseApplication.CoreSubmodules.VITALS);
            bundle.putSerializable(RegisterActivity.START_SUBMODULE_WITH_RESULT_KEY, callerSubmodule);
            startSubmodule(register,bundle);
            finish();
        }



        ActivityVitalsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_vitals);
        viewModel.getRepository().getDatabase().clientDao().findById(clientId).observe(this, binding::setClient);

        ToolBarEventHandler toolBarEventHandler = getToolbarHandler();
        toolBarEventHandler.setTitle("Capture Vitals");
        binding.setToolbarhandler(toolBarEventHandler);
    }
}
