package zm.gov.moh.drugresistanttb.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import zm.gov.moh.drugresistanttb.DrugResistantTbModule;
import zm.gov.moh.drugresistanttb.OpenmrsConfig;
import zm.gov.moh.drugresistanttb.submodule.enrollment.view.DrugResistantTbEnrollmentActivity;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.common.base.BaseEventHandler;
import zm.gov.moh.common.model.JsonForm;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.ConcurrencyUtils;
import zm.gov.moh.core.utils.Utils;

public class BaseEventDrugResistantTbHandler extends BaseEventHandler {
    Context context;
    String title;
    Bundle mBundle;

    public BaseEventDrugResistantTbHandler(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public void onMenuItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == zm.gov.moh.common.R.id.sync_action){
            synchronizeData();
        }

        else if(menuItem.getItemId() == zm.gov.moh.common.R.id.edit_action){
            BaseActivity activity  = (BaseActivity)context;
            mBundle = ((BaseActivity)this.context).getIntent().getExtras();
            try {

                JsonForm clientRegistration = new JsonForm("Edit client demographics",
                        Utils.getStringFromInputStream(activity.getAssets().open("forms/via_cervical_cancer_enrollment_edit.json")));
                mBundle.putString(Key.START_MODULE_ON_RESULT, DrugResistantTbModule.Submodules.MDR_CLIENT_ENROLLMENT);
                mBundle.putString(Key.PATIENT_ID_TYPE, OpenmrsConfig.IDENTIFIER_TYPE_MDRPIZ_UUID);
                mBundle.putSerializable(Key.JSON_FORM, clientRegistration);
                mBundle.putString(Key.ACTION, DrugResistantTbEnrollmentActivity.Action.EDIT_PATIENT);
                activity.startModule(BaseApplication.CoreModule.FORM, mBundle);

            }catch (Exception e){

            }
        }else if (menuItem.getItemId() == zm.gov.moh.common.R.id.delete_action) {
            BaseActivity activity = (BaseActivity) context;
            mBundle = ((BaseActivity) this.context).getIntent().getExtras();
            long patientID = mBundle.getLong(Key.PERSON_ID);
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("Confirm deletion");
            builder.setMessage("This client will be deleted from the mdr-tb service and  all visit data that was submitted will be lost.Proceed?");
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    activity.startModule(DrugResistantTbModule.Submodules.MDR_CLIENT_REGISTER);
                    /*ConcurrencyUtils.consumeAsync(activity.getViewModel().getRepository().getDatabase().patientIdentifierDao()::voidPatientIdentifierById, Throwable::printStackTrace, patientID);
                    Toast.makeText(activity.getBaseContext(),"Deleted successfully",Toast.LENGTH_SHORT).show();*/
                }
            });
            builder.create();
            builder.show();
        }
}}
