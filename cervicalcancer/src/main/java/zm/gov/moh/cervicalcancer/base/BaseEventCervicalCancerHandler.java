package zm.gov.moh.cervicalcancer.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import org.threeten.bp.LocalDateTime;

import androidx.appcompat.app.AlertDialog;
import androidx.core.util.Consumer;
import zm.gov.moh.cervicalcancer.CervicalCancerModule;
import zm.gov.moh.cervicalcancer.OpenmrsConfig;
import zm.gov.moh.cervicalcancer.submodule.enrollment.view.CervicalCancerEnrollmentActivity;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.common.base.BaseEventHandler;
import zm.gov.moh.common.model.JsonForm;
import zm.gov.moh.core.Constant;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.worker.RemoteWorker;
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

    public Consumer<Long> updateEntity(Database db){

        return (Long entityId) -> {

            PatientIdentifierEntity patientIdentifierEntity =  db.patientIdentifierDao().getPatientIDByIdentifierType(entityId, OpenmrsConfig.IDENTIFIER_TYPE_CCPIZ_UUID);

            patientIdentifierEntity.setVoided(Constant.VOIDED);
            patientIdentifierEntity.setDateChanged(LocalDateTime.now());

            db.patientIdentifierDao().insert(patientIdentifierEntity);

            EntityMetadata entityMetadata = db.entityMetadataDao().findEntityById(entityId);
            if (entityMetadata == null)
                entityMetadata = new EntityMetadata(entityId, EntityType.PATIENT.getId());

            entityMetadata.setLastModified(LocalDateTime.now());
            entityMetadata.setRemoteStatusCode(RemoteWorker.Status.NOT_PUSHED.getCode());



            db.entityMetadataDao().insert(entityMetadata);
        };
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
                mBundle.putString(Key.START_MODULE_ON_RESULT, CervicalCancerModule.Submodules.CLIENT_ENROLLMENT);
                mBundle.putString(Key.PATIENT_ID_TYPE, OpenmrsConfig.IDENTIFIER_TYPE_CCPIZ_UUID);
                mBundle.putSerializable(Key.JSON_FORM, clientRegistration);
                mBundle.putString(Key.ACTION, CervicalCancerEnrollmentActivity.Action.EDIT_PATIENT);
                activity.startModule(BaseApplication.CoreModule.FORM, mBundle);

            }catch (Exception e){

            }
        }else if (menuItem.getItemId() == zm.gov.moh.common.R.id.delete_action) {
            BaseActivity activity = (BaseActivity) context;
            mBundle = ((BaseActivity) this.context).getIntent().getExtras();
            final long patientId = mBundle.getLong(Key.PERSON_ID);
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("Confirm deletion");
            builder.setMessage("This client will be deleted from the cervical cancer service and  all visit data that was submitted will be lost.Proceed?");
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //ConcurrencyUtils.consumeAsync(activity.getViewModel().getRepository().getDatabase().patientIdentifierDao()::voidPatientIdentifierById, Throwable::printStackTrace, patientId);
                    Toast.makeText(activity.getBaseContext(),"Deleted successfully",Toast.LENGTH_SHORT).show();

                    Database db = activity.getViewModel().getRepository().getDatabase();

                    ConcurrencyUtils.consumeAsync(updateEntity(db)::accept,Throwable::printStackTrace, patientId);

                    activity.onBackPressed();
                }
            });
            builder.create();
            builder.show();
        }
    }}
