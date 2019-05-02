package zm.gov.moh.core.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ServiceManager {


    public static final int SERVICE_META_DATA_SYNC = 1;
    public static final int SERVICE_DATA_SYNC = 2;
    public static final int SERVICE_DEMOGRAPHICS_PERSIST = 3;
    protected Context mContext;
    protected int mService;
    protected Intent mIntent;
    protected Bundle mBundle;

    public void start(){

        switch (mService){

            case SERVICE_META_DATA_SYNC:
                mIntent = new Intent(mContext, MetaDataSync.class);

            case SERVICE_DATA_SYNC:
               mIntent = new Intent(mContext, DataSync.class);

            case SERVICE_DEMOGRAPHICS_PERSIST:
                mIntent = new Intent(mContext, DemographicsPersist.class);

        }

        if(mBundle != null)
            mIntent.putExtras(mBundle);

        mContext.startService(mIntent);
    }

    public ServiceManager setContext(Context mContext) {
        this.mContext = mContext;
        return this;
    }

    public Context getContext() {
        return mContext;
    }

    public ServiceManager setService(int service) {
        this.mService = service;
        return this;
    }

    public int getService() {
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
