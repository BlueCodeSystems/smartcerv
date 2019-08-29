package zm.gov.moh.cervicalcancer.submodule.cervicalcancer.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.BR;
import zm.gov.moh.cervicalcancer.databinding.CervicalCancerActivityBinding;
import zm.gov.moh.cervicalcancer.submodule.cervicalcancer.viewmodel.CervicalCancerViewModel;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.common.ui.ToolBarEventHandler;

public class CervicalCancerActivity extends BaseActivity {

    CervicalCancerViewModel cervicalCancerViewModel;
    TextView totalPatientsRegistered,totalSeen,totalScreened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cervicalCancerViewModel = ViewModelProviders.of(this).get(CervicalCancerViewModel.class);

        CervicalCancerActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.cervical_cancer_activity);

        binding.setVariable(BR.ccancerviewmodel, cervicalCancerViewModel);

        cervicalCancerViewModel.getStartSubmodule().observe(this,this::startModule);

        ToolBarEventHandler toolBarEventHandler = getToolbarHandler(this);
        toolBarEventHandler.setTitle("Cervical Cancer");
        binding.setToolbarhandler(toolBarEventHandler);
        binding.setContext(this);

        totalPatientsRegistered=findViewById(R.id.totalRegistered);
        totalSeen=findViewById(R.id.totalPatientsSeen);
        totalScreened=findViewById(R.id.totalScreened);
        updateDataForViews();

    }

    //update data for the views

    public void updateDataForViews()
    {
        //observe the number of registered clients
        cervicalCancerViewModel.getAllRegisteredClients().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                totalPatientsRegistered.setText(aLong.toString());
            }
        });

        //observe the number of patients seen

        cervicalCancerViewModel.getAllseenClients().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                totalSeen.setText(aLong.toString());
            }
        });

        cervicalCancerViewModel.getAllScreenedClients().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                totalScreened.setText(aLong.toString());

            }
        });


    }


}