package zm.gov.moh.core.service;

import android.content.Intent;
import android.os.Bundle;

import com.jakewharton.threetenabp.AndroidThreeTen;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import zm.gov.moh.core.model.IntentAction;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.api.rest.RestApi;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.utils.ConcurrencyUtils;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.InjectorUtils;

public abstract class RemoteService extends BaseIntentService implements InjectableViewModel {

    protected Repository repository;
    protected String accessToken = "";
    protected final int TIMEOUT = 300000;
    protected int tasksCompleted = 0;
    protected int tasksStarted = 0;
    protected Bundle mBundle;
    protected Database db;
    protected RestApi restApi;

    public RemoteService(ServiceManager.Service service){
        super(service);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        AndroidThreeTen.init(this);
        InjectorUtils.provideRepository(this,getApplication());
        db = repository.getDatabase();
        restApi = repository.getRestApi();
        mBundle = intent.getExtras();

        if(mBundle == null)
            mBundle = new Bundle();

        mBundle.putSerializable(Key.SERVICE_NAME, mService);

        ConcurrencyUtils.asyncRunnable(this::executeAsync,this::onError);
    }

    abstract protected void executeAsync();

    @Override
    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Repository getRepository() {
        return repository;
    }

    public void onError(Throwable throwable){

        Exception e = new Exception(throwable);
    }



    protected void notifySyncCompleted(){
        Intent intent = new Intent(IntentAction.REMOTE_SERVICE_COMPLETE);
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
