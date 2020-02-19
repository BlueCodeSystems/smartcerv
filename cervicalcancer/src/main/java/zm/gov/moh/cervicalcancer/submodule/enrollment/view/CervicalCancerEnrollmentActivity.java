package zm.gov.moh.cervicalcancer.submodule.enrollment.view;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

import zm.gov.moh.cervicalcancer.CervicalCancerModule;
import zm.gov.moh.cervicalcancer.OpenmrsConfig;
import zm.gov.moh.cervicalcancer.submodule.enrollment.viewmodel.CervicalCancerEnrollmentViewModel;
import zm.gov.moh.common.model.JsonForm;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.model.submodule.ModuleGroup;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.core.repository.database.entity.domain.PersonAttributeEntity;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;

public class CervicalCancerEnrollmentActivity extends BaseActivity {

    private CervicalCancerEnrollmentViewModel viewModel;
    private ModuleGroup cervicalCancerModule;
    AtomicBoolean checkObserser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_cervical_cancer_enrollment);

        viewModel = ViewModelProviders.of(this).get(CervicalCancerEnrollmentViewModel.class);
        setViewModel(viewModel);
        checkObserser = new AtomicBoolean(true);

        //viewModel.getRepository().getClientById(34).observe(this, );
        cervicalCancerModule = (ModuleGroup)((BaseApplication) this.getApplication()).getModule(CervicalCancerModule.MODULE);

        final Bundle bundle = getIntent().getExtras();

        String action = (bundle != null)? bundle.getString(Key.ACTION): "";

        long personId = bundle.getLong(Key.PERSON_ID);

        getViewModel().getRepository().getDatabase().genericDao()
                .getPatientById(personId)
                .observe(this ,patient->{

                    if(checkObserser.get()){
                        checkObserser.set(false);
                    if(action == null && patient != null) {

                        Toast.makeText(this,"Client already exists", Toast.LENGTH_LONG).show();
                        CervicalCancerEnrollmentActivity.this.finish();
                    }else if(action != null && action.equals(Action.ENROLL_PATIENT)) {

                            viewModel.enroll(bundle);
                            Toast.makeText(this,"Enrolled successfully",Toast.LENGTH_LONG).show();

                    }
                    else if(action != null && action.equals(Action.EDIT_PATIENT)) {
                            viewModel.edit(bundle);
                            Toast.makeText(this,"Edited successfully",Toast.LENGTH_LONG).show();
                    }
                    else{

                        Module formModule = ((BaseApplication)this.getApplication()).getModule(BaseApplication.CoreModule.FORM);

                            try{
                                JsonForm formJson = new JsonForm("Facility Information",
                                        Utils.getStringFromInputStream(this.getAssets().open("forms/via_cervical_cancer_enrollment.json")));

                                bundle.putSerializable(BaseActivity.JSON_FORM,formJson);
                                bundle.putString(Key.ACTION, Action.ENROLL_PATIENT);
                                bundle.putString(Key.START_MODULE_ON_RESULT, CervicalCancerModule.Submodules.CLIENT_ENROLLMENT);
                            }catch (Exception ex){

                            }

                            startModule(formModule, bundle);
                            finish();
                    }

                }
                });
        viewModel.getActionEmitter().observe(this,this::notifyCompleteAction );

        getViewModel().getRepository().getDatabase().personAttributeDao().findByPersonIdObservable(personId)
                .observe(this, personAttributeEntity -> {
                    if (personAttributeEntity != null)
                        bundle.putString(Key.PERSON_PHONE, personAttributeEntity.getValue());
                    else
                        bundle.putString(Key.PERSON_PHONE, "No telephone Number");
                });
    }

    public void notifyCompleteAction(String actionName){

        Toast toast = Toast.makeText(this,null, Toast.LENGTH_LONG);
        switch (actionName){
            case Action.ENROLL_PATIENT:
                Toast.makeText(this,"Client has been enrolled", Toast.LENGTH_LONG).show();
        }
        this.finish();
    }

    public static class Action{
        public static final String ENROLL_PATIENT = "ENROLL_PATIENT";
        public static final String EDIT_PATIENT = "EDIT_PATIENT";
    }
}