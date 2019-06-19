package zm.gov.moh.core.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedList;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import zm.gov.moh.core.model.Key;

public class ServiceManager {

    protected  Context context;
    protected Service mService;
    protected Intent mIntent;
    protected Bundle mBundle;
    protected static ServiceManager instance;
    protected ServiceBroadcastReceiver broadcastReceiver;
    private  LinkedList<Service> serviceExecutionPool;
    private LocalBroadcastManager mLocalBroadcastManager;
    private EnumMap<Service,Service> serviceSchedule;
    private  ArrayList<Service> remoteServices;



    private ServiceManager(){
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    public LinkedList<Service> getServiceExecutionPool() {
        return serviceExecutionPool;
    }

    public static ServiceManager getInstance(Context context) {


        if(instance == null) {

            instance = new ServiceManager();
            instance.context = context;
            instance.broadcastReceiver = instance.getBroadcastReceiver(instance);
            instance.serviceExecutionPool = new LinkedList<>();
            instance.remoteServices = new ArrayList<>();

            instance.remoteServices.add(Service.PULL_ENTITY_REMOTE);
            instance.remoteServices.add(Service.PULL_META_DATA_REMOTE);
            instance.remoteServices.add(Service.PULL_PATIENT_ID_REMOTE);
            instance.remoteServices.add(Service.PUSH_ENTITY_REMOTE);

            for(Service service :Service.values()){
                instance.registerIntent(IntentAction.COMPLETED + service);
                instance.registerIntent(IntentAction.INTERRUPTED + service);
            }
        }
        return instance;
    }

    public ServiceBroadcastReceiver getBroadcastReceiver(ServiceManager serviceManager) {
        if(broadcastReceiver == null)
            broadcastReceiver = new ServiceBroadcastReceiver(serviceManager);
        return broadcastReceiver;
    }

    public ServiceManager startOnComplete(Service complete, Service start){
        if(serviceSchedule == null)
            serviceSchedule = new EnumMap<>(Service.class);

        serviceSchedule.put(complete, start);
        return this;
    }

    private void registerIntent(String intentAction){
        mLocalBroadcastManager.registerReceiver(broadcastReceiver,new IntentFilter(intentAction));
    }

    public void start(){

        if(serviceExecutionPool.contains(mService)) {
            return;
        }

        switch (mService){

            case PULL_PATIENT_ID_REMOTE:
                Toast.makeText(context,"Syncing",Toast.LENGTH_LONG).show();
                mIntent = new Intent(context, PullPatientIDRemote.class);
                break;

            case PULL_META_DATA_REMOTE:
                mIntent = new Intent(context, PullMetaDataRemote.class);
                break;

            case PULL_ENTITY_REMOTE:
               mIntent = new Intent(context, PullDataRemote.class);
               break;

            case PUSH_ENTITY_REMOTE:
                mIntent = new Intent(context, PushDataRemote.class);
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
        PERSIST_ENCOUNTERS,
        PULL_PATIENT_ID_REMOTE,
        SUBSTITUTE_LOCAL_ENTITY
    }

    public class IntentAction{

        public static final String INTERRUPTED = "zm.gov.moh.common.INTERRUPTED_";
        public static final String COMPLETED = "zm.gov.moh.common.COMPLETED_";
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
            ServiceManager.Service service = (ServiceManager.Service) bundle.getSerializable(Key.SERVICE);


            final String intentActionServiceCompleted = IntentAction.COMPLETED + service;
            final String intentActionServiceInterrupted = IntentAction.INTERRUPTED + service;

            if(action != null && action.equals(intentActionServiceCompleted)){

                serviceManager.getServiceExecutionPool().remove(service);

                if(serviceSchedule.containsKey(service)) {
                    serviceManager.setService(serviceSchedule.get(service)).start();
                    serviceSchedule.remove(service);
                }

                if(service == Service.PUSH_ENTITY_REMOTE)
                    Toast.makeText(context,"Sync complete",Toast.LENGTH_LONG).show();

            }else if (action != null && action.equals(intentActionServiceInterrupted)){
                serviceManager.getServiceExecutionPool().remove(service);

                if(serviceManager.remoteServices.contains(service))
                    Toast.makeText(context,"Sync interrupted",Toast.LENGTH_LONG).show();
            }
        }
    }
}
