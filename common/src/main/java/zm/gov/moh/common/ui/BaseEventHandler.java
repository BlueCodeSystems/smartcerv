package zm.gov.moh.common.ui;

import android.content.Context;
import android.content.pm.PackageManager;


import android.os.Bundle;
import android.widget.Toast;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import zm.gov.moh.core.service.ServiceManager;
import zm.gov.moh.core.service.worker.PullDataRemoteWorker;
import zm.gov.moh.core.service.worker.PullMetaDataRemoteWorker;
import zm.gov.moh.core.service.worker.PullPatientIDRemoteWorker;
import zm.gov.moh.core.service.worker.PushDemographicDataRemoteWorker;
import zm.gov.moh.core.service.worker.PushVisitDataRemoteWorker;
import zm.gov.moh.core.utils.BaseApplication;

public class BaseEventHandler {

    Context context;
    String title;

    public BaseEventHandler(Context context) {
        this.context = context;
    }

    public void syncMetaData() {
        ServiceManager.getInstance(context)
                .setService(ServiceManager.Service.PULL_PATIENT_ID_REMOTE)
                .startOnComplete(ServiceManager.Service.PULL_PATIENT_ID_REMOTE, ServiceManager.Service.PULL_META_DATA_REMOTE)
                .startOnComplete(ServiceManager.Service.PULL_META_DATA_REMOTE, ServiceManager.Service.PULL_ENTITY_REMOTE)
                .startOnComplete(ServiceManager.Service.PULL_ENTITY_REMOTE, ServiceManager.Service.PUSH_ENTITY_REMOTE)
                .start();
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
    public PackageManager getPackageManager() {
        return context.getPackageManager();
    }

    public void setTitle(String title) {
        this.title = title;
    }

        Bundle bundle = new Bundle();



    public String getTitle() {
        return title;
    }

}


