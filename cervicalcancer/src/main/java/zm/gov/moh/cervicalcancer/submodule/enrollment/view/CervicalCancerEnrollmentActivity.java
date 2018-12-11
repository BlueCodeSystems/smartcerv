package zm.gov.moh.cervicalcancer.submodule.enrollment.view;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;

import java.util.HashMap;

import zm.gov.moh.cervicalcancer.CervicalCancerModule;
import zm.gov.moh.cervicalcancer.submodule.enrollment.viewmodel.CervicalCancerEnrollmentViewModel;
import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.model.submodule.SubmoduleGroup;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;

public class CervicalCancerEnrollmentActivity extends BaseActivity {

    private CervicalCancerEnrollmentViewModel viewModel;
    private SubmoduleGroup cervicalCancerModule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_cervical_cancer_enrollment);

        viewModel = ViewModelProviders.of(this).get(CervicalCancerEnrollmentViewModel.class);

        //viewModel.getRepository().getClientById(34).observe(this, );
        cervicalCancerModule =  (SubmoduleGroup)((BaseApplication) this.getApplication()).getSubmodule(CervicalCancerModule.SUBMODULE);

        Submodule enrollmentSubmodule = cervicalCancerModule.getSubmodule(CervicalCancerModule.Submodules.CLIENT_ENROLLMENT);
        Bundle bundle = getIntent().getExtras();

        String action = (bundle != null)? bundle.getString(BaseActivity.ACTION_KEY): "";


        switch (action){

            case Action.ENROLL_PATIENT:
                HashMap<String,Object> formData = (HashMap<String,Object>) bundle.getSerializable(BaseActivity.FORM_DATA_KEY);
                viewModel.enrollPatient(formData);
                break;

            default:

                Submodule formSubmodule = ((BaseApplication)this.getApplication()).getSubmodule(BaseApplication.CoreSubmodules.FORM);

                try{
                    String json = Utils.getStringFromInputStream(this.getAssets().open("forms/cervical_cancer_enrollment.json"));

                    if(bundle == null)
                        bundle = new Bundle();

                    bundle.putString(BaseActivity.JSON_FORM_KEY,json);
                    bundle.putString(BaseActivity.ACTION_KEY, Action.ENROLL_PATIENT);
                    bundle.putSerializable(BaseActivity.START_SUBMODULE_ON_FORM_RESULT_KEY, enrollmentSubmodule);
                }catch (Exception ex){

                }

                startSubmodule(formSubmodule, bundle);
                finish();
        }
    }

    public class Action{
        static final String ENROLL_PATIENT = "ENROLL_PATIENT";
    }
}
