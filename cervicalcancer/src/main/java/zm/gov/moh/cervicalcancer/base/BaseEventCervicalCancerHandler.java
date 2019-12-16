package zm.gov.moh.cervicalcancer.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import zm.gov.moh.cervicalcancer.CervicalCancerModule;
import zm.gov.moh.cervicalcancer.submodule.enrollment.view.CervicalCancerEnrollmentActivity;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.common.base.BaseEventHandler;
import zm.gov.moh.common.model.JsonForm;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.ConcurrencyUtils;
import zm.gov.moh.core.utils.Utils;

public class BaseEventCervicalCancerHandler extends BaseEventHandler {
    Context context;
    String title;
    Bundle mBundle;

    public BaseEventCervicalCancerHandler(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onMenuItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == zm.gov.moh.common.R.id.sync_action){
            syncData();
        }

        else if(menuItem.getItemId() == zm.gov.moh.common.R.id.edit_action){
            BaseActivity activity  = (BaseActivity)context;
            mBundle = ((BaseActivity)this.context).getIntent().getExtras();
            try {

                JsonForm clientRegistration = new JsonForm("Edit client demographics",
                        Utils.getStringFromInputStream(activity.getAssets().open("forms/via_cervical_cancer_enrollment.json")));
                mBundle.putString(Key.START_MODULE_ON_RESULT, CervicalCancerModule.Submodules.CLIENT_ENROLLMENT);
                mBundle.putSerializable(Key.JSON_FORM, clientRegistration);
                mBundle.putString(Key.ACTION, CervicalCancerEnrollmentActivity.Action.EDIT_PATIENT);
                activity.startModule(BaseApplication.CoreModule.FORM, mBundle);

            }catch (Exception e){

            }
    }else if (menuItem.getItemId() == zm.gov.moh.common.R.id.deleteEntry) {
            BaseActivity activity = (BaseActivity) context;
            mBundle = ((BaseActivity) this.context).getIntent().getExtras();
            long patientID = mBundle.getLong(Key.PERSON_ID);
            activity.startModule(CervicalCancerModule.Submodules.CLIENT_REGISTER);
           ConcurrencyUtils.consumeAsync(activity.getViewModel().getRepository().getDatabase().patientIdentifierDao()::voidPatientIdentifierById, Throwable::printStackTrace, patientID);
            Toast.makeText(activity.getBaseContext(),"Deleted successfully",Toast.LENGTH_SHORT).show();

        }
}}
