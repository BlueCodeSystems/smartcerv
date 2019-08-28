package zm.gov.moh.common.submodule.vitals.view;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.ActivityVitalsBinding;
import zm.gov.moh.common.submodule.dashboard.client.view.ClientDashboardActivity;
import zm.gov.moh.common.submodule.vitals.viewmodel.VitalsViewModel;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.common.ui.BaseEventHandler;

public class VitalsActivity extends BaseActivity {

    Bundle bundle;
    VitalsViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getIntent().getExtras();

        viewModel = ViewModelProviders.of(this).get(VitalsViewModel.class);

        setViewModel(viewModel);

        long clientId = bundle.getLong(ClientDashboardActivity.PERSON_ID);
        initBundle(bundle);

        ActivityVitalsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_vitals);
        binding.setViewmodel(viewModel);
        viewModel.getRepository().getDatabase().clientDao().findById(clientId).observe(this, binding::setClient);
        binding.setVitals(viewModel.getVitals());
        binding.setBundle(bundle);
        binding.setContext(this);

        BaseEventHandler toolBarEventHandler = getToolbarHandler(this);
        toolBarEventHandler.setTitle("Capture Vitals");
        binding.setToolbarhandler(toolBarEventHandler);
    }

    public void onSubmit(Bundle bundle){

        viewModel.onSubmit(bundle);

        finish();
    }
}