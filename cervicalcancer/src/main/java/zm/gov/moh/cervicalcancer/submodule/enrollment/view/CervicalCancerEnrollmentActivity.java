package zm.gov.moh.cervicalcancer.submodule.enrollment.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import java.util.HashMap;

import zm.gov.moh.cervicalcancer.submodule.enrollment.viewmodel.CervicalCancerEnrollmentViewModel;
import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.model.submodule.SubmoduleGroup;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;

public class CervicalCancerEnrollmentActivity extends BaseActivity {

    private CervicalCancerEnrollmentViewModel mCervicalCancerEnrollmentViewModel;
    private SubmoduleGroup enroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_cervical_cancer_enrollment);

        mCervicalCancerEnrollmentViewModel = ViewModelProviders.of(this).get(CervicalCancerEnrollmentViewModel.class);

        //mCervicalCancerEnrollmentViewModel.getRepository().getClientById(34).observe(this, );
        enroll =  (SubmoduleGroup)((BaseApplication) this.getApplication()).getSubmodule(BaseApplication.CareSubmodules.CERVICAL_CANCER);

        Submodule submodule = enroll.getSubmodules().get(0);
        Bundle bundle = getIntent().getExtras();

        String action = (bundle != null)? bundle.getString(BaseActivity.ACTION_KEY): "";


        switch (action){

            case Action.ENROLL_PATIENT:
                HashMap<String,Object> formData = (HashMap<String,Object>) bundle.getSerializable(BaseActivity.FORM_DATA_KEY);
                mCervicalCancerEnrollmentViewModel.enrollPatient(formData);
                break;

            default:

                Submodule formSubmodule = ((BaseApplication)this.getApplication()).getSubmodule(BaseApplication.CoreSubmodules.FORM);

                try{
                    String json = Utils.getStringFromInputStream(this.getAssets().open("forms/cervical_cancer_enrollment.json"));

                    if(bundle == null)
                        bundle = new Bundle();

                    bundle.putString(BaseActivity.JSON_FORM_KEY,json);
                    bundle.putString(BaseActivity.ACTION_KEY, Action.ENROLL_PATIENT);
                    bundle.putSerializable(BaseActivity.START_SUBMODULE_ON_FORM_RESULT_KEY, submodule);
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
