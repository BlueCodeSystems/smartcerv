package zm.gov.moh.core.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import java.util.LinkedList;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import zm.gov.moh.core.model.Key;

public class ServiceManager {

    protected static Context context;
    protected Service mService;
    protected Intent mIntent;
    protected Bundle mBundle;
    protected static ServiceManager instance;
    protected static ServiceBroadcastReceiver broadcastReceiver;
    private  static LinkedList<Service> serviceExecutionPool;



    private ServiceManager(){

    }

    public static LinkedList<Service> getServiceExecutionPool() {
        return serviceExecutionPool;
    }

    public static ServiceManager getInstance(Context context) {
        ServiceManager.context = context;

        if(instance == null) {

            instance = new ServiceManager();
            broadcastReceiver = instance.getBroadcastReceiver(instance);
            serviceExecutionPool = new LinkedList<>();


            LocalBroadcastManager.getInstance(context)
                    .registerReceiver(broadcastReceiver, new IntentFilter(IntentAction.PULL_META_DATA_REMOTE_COMPLETE));

            LocalBroadcastManager.getInstance(context)
                    .registerReceiver(broadcastReceiver, new IntentFilter(IntentAction.PULL_ENTITY_REMOTE_COMPLETE));

            LocalBroadcastManager.getInstance(context)
                    .registerReceiver(broadcastReceiver, new IntentFilter(IntentAction.PUSH_ENTITY_REMOTE_COMPLETE));

            LocalBroadcastManager.getInstance(context)
                    .registerReceiver(broadcastReceiver, new IntentFilter(IntentAction.PERSIST_ENCOUNTERS_REMOTE_COMPLETE));

            //interrupts
            LocalBroadcastManager.getInstance(context)
                    .registerReceiver(broadcastReceiver, new IntentFilter(IntentAction.PULL_META_DATA_REMOTE_INTERRUPT));

            LocalBroadcastManager.getInstance(context)
                    .registerReceiver(broadcastReceiver, new IntentFilter(IntentAction.PULL_ENTITY_REMOTE_INTERRUPT));

            LocalBroadcastManager.getInstance(context)
                    .registerReceiver(broadcastReceiver, new IntentFilter(IntentAction.PUSH_ENTITY_REMOTE_INTERRUPT));

            LocalBroadcastManager.getInstance(context)
                    .registerReceiver(broadcastReceiver, new IntentFilter(IntentAction.PERSIST_ENCOUNTERS_REMOTE_INTERRUPT));
        }
        return instance;
    }

    public ServiceBroadcastReceiver getBroadcastReceiver(ServiceManager serviceManager) {
        if(broadcastReceiver == null)
            broadcastReceiver = new ServiceBroadcastReceiver(serviceManager);
        return broadcastReceiver;
    }

    public void start(){

        if(serviceExecutionPool.contains(mService)) {
            Toast.makeText(context,mService.toString()+":already running",Toast.LENGTH_LONG).show();
            return;
        }

        switch (mService){

            case PULL_META_DATA_REMOTE:
                mIntent = new Intent(context, PullMetaDataRemote.class);
                Toast.makeText(context,"Syncing",Toast.LENGTH_LONG).show();
                break;

            case PULL_ENTITY_REMOTE:
               mIntent = new Intent(context, PullEntityRemote.class);
               break;

            case PUSH_ENTITY_REMOTE:
                mIntent = new Intent(context, PushEntityRemote.class);
                break;

            case PERSIST_DEMOGRAPHICS:
                mIntent = new Intent(context, PersistDemographics.class);
                break;

            case PERSIST_ENCOUNTERS:
                mIntent = new Intent(context, PersistEncounter.class);
                break;
        }

        if(mBundle != null)
            mIntent.putExtras(mBundle);

        context.startService(mIntent);
        serviceExecutionPool.add(mService);
    }

    public ServiceManager setContext(Context mContext) {
        this.context = mContext;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public ServiceManager setService(Service service) {
        this.mService = service;
        return this;
    }

    public Service getService() {
        return mService;
    }

    public ServiceManager putExtras(Bundle mBundle) {
        this.mBundle = mBundle;
        return this;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public enum Service{
        PULL_META_DATA_REMOTE,
        PULL_ENTITY_REMOTE,
        PUSH_ENTITY_REMOTE,
        PERSIST_DEMOGRAPHICS,
        PERSIST_ENCOUNTERS
    }

    public class IntentAction{
        //Complete
         public static final String PULL_META_DATA_REMOTE_COMPLETE = "zm.gov.moh.common.PULL_META_DATA_REMOTE_COMPLETE";
         public static final String PULL_ENTITY_REMOTE_COMPLETE = "zm.gov.moh.common.PULL_ENTITY_REMOTE_COMPLETE";
         public static final String PUSH_ENTITY_REMOTE_COMPLETE = "zm.gov.moh.common.PUSH_ENTITY_REMOTE_COMPLETE";
         public static final String PERSIST_ENCOUNTERS_REMOTE_COMPLETE = "zm.gov.moh.common.PERSIST_ENCOUNTERS_REMOTE_COMPLETE";

         //Interrupt
        public static final String PULL_META_DATA_REMOTE_INTERRUPT = "zm.gov.moh.common.PULL_META_DATA_REMOTE_INTERRUPT";
        public static final String PULL_ENTITY_REMOTE_INTERRUPT = "zm.gov.moh.common.PULL_ENTITY_REMOTE_INTERRUPT";
        public static final String PUSH_ENTITY_REMOTE_INTERRUPT = "zm.gov.moh.common.PUSH_ENTITY_REMOTE_INTERRUPT";
        public static final String PERSIST_ENCOUNTERS_REMOTE_INTERRUPT = "zm.gov.moh.common.PERSIST_ENCOUNTERS_REMOTE_INTERRUPT";
    }

    public class ServiceBroadcastReceiver extends BroadcastReceiver{

        private ServiceManager serviceManager;

        public ServiceBroadcastReceiver(ServiceManager serviceManager){
            this.serviceManager = serviceManager;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle bundle;

            bundle = intent.getExtras();
            ServiceManager.Service serviceName = (ServiceManager.Service) bundle.getSerializable(Key.SERVICE_NAME);

            switch (action){

                case IntentAction.PULL_META_DATA_REMOTE_COMPLETE:
                    serviceManager.getServiceExecutionPool().remove(serviceName);
                    serviceManager.setService(Service.PULL_ENTITY_REMOTE).start();

                    Toast.makeText(context,"PULL_ENTITY_REMOTE",Toast.LENGTH_LONG).show();

                    break;
                case IntentAction.PULL_ENTITY_REMOTE_COMPLETE:
                    serviceManager.getServiceExecutionPool().remove(serviceName);
                    serviceManager.setService(Service.PUSH_ENTITY_REMOTE).start();

                    Toast.makeText(context,"PUSH_ENTITY_REMOTE",Toast.LENGTH_LONG).show();
                    break;
                case IntentAction.PUSH_ENTITY_REMOTE_COMPLETE:
                    Toast.makeText(context,"Sync complete",Toast.LENGTH_LONG).show();
                    serviceManager.getServiceExecutionPool().remove(serviceName);
                    break;

                case IntentAction.PERSIST_ENCOUNTERS_REMOTE_COMPLETE:
                    Toast.makeText(context,"Encounters saved",Toast.LENGTH_LONG).show();
                    serviceManager.getServiceExecutionPool().remove(serviceName);
                    break;

                //Interrupt
                case IntentAction.PULL_META_DATA_REMOTE_INTERRUPT:
                    serviceManager.getServiceExecutionPool().remove(serviceName);
                    Toast.makeText(context,"PULL_ENTITY_REMOTE: Interrupted",Toast.LENGTH_LONG).show();

                    break;
                case IntentAction.PULL_ENTITY_REMOTE_INTERRUPT:
                    serviceManager.getServiceExecutionPool().remove(serviceName);
                    Toast.makeText(context,"PUSH_ENTITY_REMOTE: Interrupted",Toast.LENGTH_LONG).show();
                    break;

                case IntentAction.PUSH_ENTITY_REMOTE_INTERRUPT:
                    Toast.makeText(context,"Sync complete : Interrupted",Toast.LENGTH_LONG).show();
                    serviceManager.getServiceExecutionPool().remove(serviceName);
                    break;

                case IntentAction.PERSIST_ENCOUNTERS_REMOTE_INTERRUPT:
                    Toast.makeText(context,"Encounters saving Interrupted",Toast.LENGTH_LONG).show();
                    serviceManager.getServiceExecutionPool().remove(serviceName);
                    break;
            }
        }
    }
}
