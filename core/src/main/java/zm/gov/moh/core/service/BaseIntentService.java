package zm.gov.moh.core.service;

import android.app.IntentService;

public abstract class BaseIntentService extends IntentService {

    protected ServiceManager.Service mService;

    public BaseIntentService(ServiceManager.Service service){
        super(service.toString());
        this.mService = service;
    }

}
