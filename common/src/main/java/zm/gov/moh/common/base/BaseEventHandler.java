package zm.gov.moh.common.base;

import android.content.Context;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.threeten.bp.LocalDateTime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import zm.gov.moh.common.R;
import zm.gov.moh.common.model.JsonForm;
import zm.gov.moh.core.model.IntentAction;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.worker.PullDataRemoteWorker;
import zm.gov.moh.core.service.worker.PullMetaDataRemoteWorker;
import zm.gov.moh.core.service.worker.PullPatientIDRemoteWorker;
import zm.gov.moh.core.service.worker.PushDemographicDataRemoteWorker;
import zm.gov.moh.core.service.worker.PushVisitDataRemoteWorkerSets;
import zm.gov.moh.core.service.worker.RemoteWorker;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.ConcurrencyUtils;
import zm.gov.moh.core.utils.Utils;

public class BaseEventHandler implements View.OnLongClickListener {

    Context context;
    String title;
    Bundle mBundle;
    WorkManager workManager;

    public BaseEventHandler(Context context) {
        this.context = context;


    }

    public void synchronizeData() {

        BaseApplication applicationContext =(BaseApplication) context.getApplicationContext();
        if(!applicationContext.isSynchronizing())
            applicationContext.setSynchronizing(true);
        else {
            Toast.makeText(context, "Synchronization in progress", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(context, "Synchronizing", Toast.LENGTH_LONG).show();


        workManager = WorkManager.getInstance(context);
        workManager.cancelAllWork();
        OneTimeWorkRequest workRequestPullIdData = new OneTimeWorkRequest.Builder(PullPatientIDRemoteWorker.class).build();
        OneTimeWorkRequest workRequestData = new OneTimeWorkRequest.Builder(PullDataRemoteWorker.class).build();
        OneTimeWorkRequest workRequestMetadata = new OneTimeWorkRequest.Builder(PullMetaDataRemoteWorker.class).build();
        OneTimeWorkRequest workRequestPushDemoData = new OneTimeWorkRequest.Builder(PushDemographicDataRemoteWorker.class).build();
        OneTimeWorkRequest workRequestPushVisitData = new OneTimeWorkRequest.Builder(PushVisitDataRemoteWorkerSets.class).build();
        workManager.beginWith(workRequestPullIdData).then(workRequestMetadata).then(workRequestData).then(workRequestPushDemoData).then(workRequestPushVisitData).enqueue();
        //workManager.beginWith(workRequestPullIdData).then(workRequestMetadata).then(workRequestPushDemoData).then(workRequestPushVisitData).enqueue();
        //workManager.beginWith(workRequestPushVisitData).then(workRequestPushDemoData).enqueue();



    }


    public void onClicklogOut() {
        ((BaseActivity) context).startModule(BaseApplication.CoreModule.BOOTSTRAP);
    }

    public void enableLongClick(Menu menu){

        ConcurrencyUtils.asyncRunnable(() -> {

            try {
                Thread.sleep(500);
                final View actionView = ((AppCompatActivity) context).findViewById(android.R.id.content).getRootView();

                for (int i = 0; i < menu.size(); i++) {
                    int id = menu.getItem(i).getItemId();
                    View view = actionView.findViewById(id);
                    if(view != null)
                        view.setOnLongClickListener(this);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        },Throwable::printStackTrace);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void onMenuItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.sync_action) {
            synchronizeData();
        } else if (menuItem.getItemId() == R.id.edit_action) {
            BaseActivity activity = (BaseActivity) context;
            mBundle = ((BaseActivity) this.context).getIntent().getExtras();
            try {

                JsonForm clientRegistration = new JsonForm("Edit client demographics",
                        Utils.getStringFromInputStream(activity.getAssets().open("forms/client_demographics_edit.json")));
                mBundle.putSerializable(Key.JSON_FORM, clientRegistration);
                activity.startModule(BaseApplication.CoreModule.FORM, mBundle);

            } catch (Exception e) {

            }
        } else if (menuItem.getItemId() == R.id.delete_action) {
            BaseActivity activity = (BaseActivity) context;
            mBundle = ((BaseActivity) this.context).getIntent().getExtras();
            long patientID = mBundle.getLong(Key.PERSON_ID);

            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("Confirm deletion");
            builder.setMessage("All visit data that was submitted for this client  will be lost.Proceed?");
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    ConcurrencyUtils.consumeAsync(delete(activity)::accept, Throwable::printStackTrace, patientID);
                    Toast.makeText(activity.getBaseContext(),"Deleted successfully",Toast.LENGTH_SHORT).show();
                    activity.onBackPressed();
                }
            });
            builder.create();
            builder.show();
        }
    }

    public Consumer<Long> delete(BaseActivity context) {


        return (Long patientId) -> {

            Database db = context.viewModel.getRepository().getDatabase();

            db.patientIdentifierDao().voidPatientById(patientId);

            Person person = db.personDao().findById(patientId);
            person.setVoided((short)1);
            db.personDao().insert(person);

            //Queue for pushing changes to remote
            EntityMetadata entityMetadata = db.entityMetadataDao().findEntityById(patientId);
            if(entityMetadata == null)
                entityMetadata = new EntityMetadata(patientId, EntityType.PATIENT.getId());

            entityMetadata.setLastModified(LocalDateTime.now());
            entityMetadata.setRemoteStatusCode(RemoteWorker.Status.NOT_PUSHED.getCode());
            db.entityMetadataDao().insert(entityMetadata);
        };
    }

    @Override
    public boolean onLongClick(View view) {

        if(view.getId() == R.id.sync_action){
            LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(IntentAction.REMOTE_SERVICE_INTERRUPTED));
            if(workManager != null)
                workManager.cancelAllWork();
        }
        return false;
    }
}


