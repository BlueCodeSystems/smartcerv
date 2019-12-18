package zm.gov.moh.drugresistanttb.submodule.enrollment.view;

import androidx.lifecycle.ViewModelProviders;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.common.model.JsonForm;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.model.submodule.ModuleGroup;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;
import zm.gov.moh.drugresistanttb.DrugResistantTbModule;
import zm.gov.moh.drugresistanttb.R;
import zm.gov.moh.drugresistanttb.submodule.enrollment.viewModel.DrugResistantTbEnrollmentViewModel;
import android.os.Bundle;
import android.widget.Toast;

public class DrugResistantTbEnrollmentActivity extends BaseActivity {

    private DrugResistantTbEnrollmentViewModel viewModel;
    private ModuleGroup DrugResistantTbModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_resistant_tb_enrollment);

        viewModel = ViewModelProviders.of(this).get(DrugResistantTbEnrollmentViewModel.class);
        setViewModel(viewModel);

        //viewModel.getRepository().getClientById(34).observe(this, );
        DrugResistantTbModule = (ModuleGroup)((BaseApplication) this.getApplication()).getModule(zm.gov.moh.drugresistanttb.DrugResistantTbModule.MODULE);

        final Bundle bundle = getIntent().getExtras();

        String action = (bundle != null)? bundle.getString(BaseActivity.ACTION_KEY): "";

        long personId = bundle.getLong(Key.PERSON_ID);

        getViewModel().getRepository().getDatabase().genericDao()
                .getPatientById(personId)
                .observe(this ,patient->{
                    if(patient != null) {

                        Toast toast = Toast.makeText(this,null, Toast.LENGTH_LONG);
                        if(bundle.containsKey(BaseActivity.ACTION_KEY))
                            toast.setText("Client has been enrolled");
                        else
                            toast.setText("Client already exists");

                        toast.show();
                        DrugResistantTbEnrollmentActivity.this.finish();
                    }else {

                        if(action != null && action.equals(Action.ENROLL_PATIENT)) {

                            viewModel.enrollPatient(bundle);

                        }
                        else{

                            Module formModule = ((BaseApplication)this.getApplication()).getModule(BaseApplication.CoreModule.FORM);

                            try{
                                JsonForm formJson = new JsonForm("Facility Information",
                                        Utils.getStringFromInputStream(this.getAssets().open("forms/drug_resistant_tb_enrollment.json")));

                                bundle.putSerializable(BaseActivity.JSON_FORM,formJson);
                                bundle.putString(BaseActivity.ACTION_KEY, Action.ENROLL_PATIENT);
                                bundle.putString(Key.START_MODULE_ON_RESULT, zm.gov.moh.drugresistanttb.DrugResistantTbModule.Submodules.MDR_CLIENT_ENROLLMENT);
                            }catch (Exception ex){

                            }

                            startModule(formModule, bundle);
                            finish();
                        }
                    }
                });


    }

    public class Action{
        static final String ENROLL_PATIENT = "ENROLL_PATIENT";
    }

}


