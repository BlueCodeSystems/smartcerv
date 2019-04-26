package zm.gov.moh.core.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.jakewharton.threetenabp.AndroidThreeTen;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import zm.gov.moh.core.model.IntentAction;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.InjectorUtils;

public abstract class SyncService extends IntentService implements InjectableViewModel {

    protected Repository repository;
    protected String accesstoken = "";
    protected final int TIMEOUT = 300000;
    protected int tasksCompleted = 0;
    protected int tasksStarted = 0;
    protected Bundle mBundle;
    protected String mName;

    public SyncService(String name){
        super(name);
        this.mName = name;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        AndroidThreeTen.init(this);
        InjectorUtils.provideRepository(this,getApplication());
        mBundle = intent.getExtras();

        if(mBundle == null)
            mBundle = new Bundle();

        mBundle.putString(Key.SERVICE_NAME, mName);
    }

    @Override
    public void setRepository(Repository repository) {
        this.repository = repository;
    }


    public void onError(Throwable throwable){
        Exception e = new Exception(throwable);
    }



    private void notifySyncCompleted(){
        Intent intent = new Intent(IntentAction.SYNC_COMPLETE);
        intent.putExtras(mBundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void onTaskStarted(){
        tasksStarted++;
    }

    public void onTaskCompleted(){

        tasksCompleted++;

        if(tasksCompleted == tasksStarted)
            notifySyncCompleted();
    }
}
