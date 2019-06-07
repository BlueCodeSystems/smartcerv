package zm.gov.moh.core.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import zm.gov.moh.core.model.Key;

public abstract class BaseIntentService extends IntentService {

    protected ServiceManager.Service mService;
    protected Bundle mBundle;

    public BaseIntentService(ServiceManager.Service service){
        super(service.toString());
        this.mService = service;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        mBundle = intent.getExtras();

        if(mBundle == null)
            mBundle = new Bundle();

        mBundle.putSerializable(Key.SERVICE_NAME, mService);
    }
}
