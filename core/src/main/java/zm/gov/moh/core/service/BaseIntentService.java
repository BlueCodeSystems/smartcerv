package zm.gov.moh.core.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import zm.gov.moh.core.model.Key;

public abstract class BaseIntentService extends IntentService {

    protected ServiceManager.Service mService;
    protected Bundle mBundle;
    protected LocalBroadcastManager mLocalBroadcastManager;

    public BaseIntentService(ServiceManager.Service service){
        super(service.toString());
        this.mService = service;
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        mBundle = intent.getExtras();

        if(mBundle == null)
            mBundle = new Bundle();

        mBundle.putSerializable(Key.SERVICE, mService);
    }

    protected void notifyCompleted() {
        Intent intent = new Intent(ServiceManager.IntentAction.COMPLETED + mService);
        intent.putExtras(mBundle);
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    protected void notifyInterrupted() {
        Intent intent = new Intent(ServiceManager.IntentAction.INTERRUPTED + mService);
        intent.putExtras(mBundle);
        mLocalBroadcastManager.sendBroadcast(intent);
    }
}
