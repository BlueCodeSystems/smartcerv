package zm.gov.moh.core.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ServiceManager {


    public static final String SERVICE_PULL_META_DATA_REMOTE = "pull metadata remote";
    public static final String SERVICE_PULL_ENTITY_REMOTE = "pull entity remote";
    public static final String SERVICE_PUSH_ENTITY_REMOTE = "push entity remote";
    public static final String SERVICE_PERSIST_DEMOGRAPHICS = "demographics persist";

    protected static Context context;
    protected String mService;
    protected Intent mIntent;
    protected Bundle mBundle;
    protected static ServiceManager instance;

    public ServiceManager(){

    }

    public static ServiceManager getInstance(Context context) {
        ServiceManager.context = context;
        if(instance == null)
            instance = new ServiceManager();
        return instance;
    }

    public void start(){

        switch (mService){

            case SERVICE_PULL_META_DATA_REMOTE:
                mIntent = new Intent(context, PullMetaDataRemote.class);
                break;

            case SERVICE_PULL_ENTITY_REMOTE:
               mIntent = new Intent(context, PullEntityRemote.class);
               break;

            case SERVICE_PUSH_ENTITY_REMOTE:
                mIntent = new Intent(context, PushEntityRemote.class);
                break;

            case SERVICE_PERSIST_DEMOGRAPHICS:
                mIntent = new Intent(context, PersistDemographics.class);
                break;
        }

        if(mBundle != null)
            mIntent.putExtras(mBundle);

        context.startService(mIntent);
    }

    public ServiceManager setContext(Context mContext) {
        this.context = mContext;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public ServiceManager setService(String service) {
        this.mService = service;
        return this;
    }

    public String getService() {
        return mService;
    }

    public ServiceManager setBundle(Bundle mBundle) {
        this.mBundle = mBundle;
        return this;
    }

    public Bundle getBundle() {
        return mBundle;
    }
}
