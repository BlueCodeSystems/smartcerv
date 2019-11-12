package zm.gov.moh.common.base;

import android.content.Context;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import zm.gov.moh.common.R;
import zm.gov.moh.common.model.JsonForm;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.service.worker.PullDataRemoteWorker;
import zm.gov.moh.core.service.worker.PullMetaDataRemoteWorker;
import zm.gov.moh.core.service.worker.PullPatientIDRemoteWorker;
import zm.gov.moh.core.service.worker.PushDemographicDataRemoteWorker;
import zm.gov.moh.core.service.worker.PushVisitDataRemoteWorker;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;

public class BaseEventHandler {

    Context context;
    String title;

    public BaseEventHandler(Context context) {
        this.context = context;
    }

    public void syncData(){
        Toast.makeText(context,"Syncing",Toast.LENGTH_LONG).show();
        WorkManager workManager = WorkManager.getInstance(context);
        OneTimeWorkRequest workRequestPullIdData = new OneTimeWorkRequest.Builder(PullPatientIDRemoteWorker.class).build();
        OneTimeWorkRequest workRequestData = new OneTimeWorkRequest.Builder(PullDataRemoteWorker.class).build();
        OneTimeWorkRequest workRequestMetadata = new OneTimeWorkRequest.Builder(PullMetaDataRemoteWorker.class).build();
        OneTimeWorkRequest workRequestPushDemoData = new OneTimeWorkRequest.Builder(PushDemographicDataRemoteWorker.class).build();
        OneTimeWorkRequest workRequestPushVisitData = new OneTimeWorkRequest.Builder(PushVisitDataRemoteWorker.class).build();
        workManager.beginWith(workRequestPullIdData).then(workRequestMetadata).then(workRequestData).then(workRequestPushDemoData).then(workRequestPushVisitData).enqueue();
    }

    public void onClicklogOut() {
        ((BaseActivity)context).startModule(BaseApplication.CoreModule.BOOTSTRAP);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void onMenuItemSelected(MenuItem menuItem){

        if (menuItem.getItemId() == R.id.sync_action){
            syncData();
        }

        else if(menuItem.getItemId() == R.id.edit_action){
            BaseActivity activity  = (BaseActivity)context;

            Bundle mBundle = activity.getIntent().getExtras();

            try {

                JsonForm clientRegistration = new JsonForm("Edit client demographics",
                        Utils.getStringFromInputStream(activity.getAssets().open("forms/client_demographics_edit.json")));


                mBundle.putSerializable(Key.JSON_FORM, clientRegistration);
                activity.startModule(BaseApplication.CoreModule.FORM, mBundle);

            }catch (Exception e){

            }
        }
    }
}


