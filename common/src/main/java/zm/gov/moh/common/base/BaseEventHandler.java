package zm.gov.moh.common.base;

import android.content.Context;


import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import zm.gov.moh.common.R;
import zm.gov.moh.common.model.JsonForm;
import zm.gov.moh.common.submodule.form.model.Action;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.service.worker.PullDataRemoteWorker;
import zm.gov.moh.core.service.worker.PullMetaDataRemoteWorker;
import zm.gov.moh.core.service.worker.PullPatientIDRemoteWorker;
import zm.gov.moh.core.service.worker.PushDemographicDataRemoteWorker;
import zm.gov.moh.core.service.worker.PushVisitDataRemoteWorker;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.ConcurrencyUtils;
import zm.gov.moh.core.utils.Utils;

public class BaseEventHandler {

    Context context;
    String title;
    Bundle mBundle;

    public BaseEventHandler(Context context) {
        this.context = context;


    }

    public void syncData() {
        Toast.makeText(context, "Syncing", Toast.LENGTH_LONG).show();
        WorkManager workManager = WorkManager.getInstance(context);
        OneTimeWorkRequest workRequestPullIdData = new OneTimeWorkRequest.Builder(PullPatientIDRemoteWorker.class).build();
        OneTimeWorkRequest workRequestData = new OneTimeWorkRequest.Builder(PullDataRemoteWorker.class).build();
        OneTimeWorkRequest workRequestMetadata = new OneTimeWorkRequest.Builder(PullMetaDataRemoteWorker.class).build();
        OneTimeWorkRequest workRequestPushDemoData = new OneTimeWorkRequest.Builder(PushDemographicDataRemoteWorker.class).build();
        OneTimeWorkRequest workRequestPushVisitData = new OneTimeWorkRequest.Builder(PushVisitDataRemoteWorker.class).build();
        workManager.beginWith(workRequestPullIdData).then(workRequestMetadata).then(workRequestData).then(workRequestPushDemoData).then(workRequestPushVisitData).enqueue();
    }

    public void onClicklogOut() {
        ((BaseActivity) context).startModule(BaseApplication.CoreModule.BOOTSTRAP);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void onMenuItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.sync_action) {
            syncData();
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
        } else if (menuItem.getItemId() == R.id.deleteEntry) {
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
                    //mBundle.putLong("identifier",3);
                   // activity.startModule(BaseApplication.CoreModule.REGISTER,mBundle);
                    activity.onBackPressed();


                    ConcurrencyUtils.consumeAsync(activity.viewModel.getRepository().getDatabase().patientIdentifierDao()::voidPatientById, Throwable::printStackTrace, patientID);
                    Toast.makeText(activity.getBaseContext(),"Deleted successfully",Toast.LENGTH_SHORT).show();
                }
            });
            builder.create();
            builder.show();
        }

        }
    }


