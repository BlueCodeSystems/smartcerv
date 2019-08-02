package zm.gov.moh.core.service;

import android.content.Intent;

import androidx.annotation.Nullable;

import com.jakewharton.threetenabp.AndroidThreeTen;

import zm.gov.moh.core.repository.api.rest.RestApi;


public abstract class RemoteService extends BaseIntentService {

    protected String accessToken ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7InV1aWQiOiJhM2FjYTE4MS1kMDJhLTRiODgtOTc1NC1lYWM0NWQzZGUzZmUiLCJkaXNwbGF5IjoiYW50aG9ueSIsInVzZXJuYW1lIjoiYW50aG9ueSIsInN5c3RlbUlkIjoiMy00In0sImlhdCI6MTU0MjE0MzU3NiwiZXhwIjoxNTkyMTQzNTc2fQ.DsDbPXwaZ5sg2SFCq1CBykITJjog-9u-4XzNGw9IYV8";
    protected final int TIMEOUT = 300000;
    protected int tasksCompleted = 0;
    protected int tasksStarted = 0;
    protected RestApi restApi;

    public RemoteService(ServiceManager.Service service){
        super(service);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        super.onHandleIntent(intent);
        AndroidThreeTen.init(this);
        //TODO: replace hard coded token with dynamically assigned tokens
        //accessToken = getRepository().getDefaultSharePrefrences().getString(Key.ACCESS_TOKEN,null);

        if(accessToken == null){

            notifyInterrupted();
            return;
        }

        restApi = repository.getRestApi();
    }

    public void onTaskStarted(){
        tasksStarted++;
    }

    public void onTaskCompleted(){

        tasksCompleted++;

        if(tasksCompleted == tasksStarted)
            notifyCompleted();
    }

    public enum Status{

        SYNCED((short)1),PUSHED((short)2),PULLED((short)3);
        private short code;

        Status(short code){
            this.code = code;
        }

        public short getCode(){
            return this.code;
        }
    }
}