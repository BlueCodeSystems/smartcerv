package zm.gov.moh.common.base;

import android.content.Context;


import android.widget.Toast;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
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

}


