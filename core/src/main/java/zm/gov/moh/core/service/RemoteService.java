package zm.gov.moh.core.service;

import android.content.Intent;

import com.jakewharton.threetenabp.AndroidThreeTen;

import androidx.annotation.Nullable;
import zm.gov.moh.core.repository.api.rest.RestApi;


public abstract class RemoteService extends BaseIntentService {

    protected String accessToken = "";
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
