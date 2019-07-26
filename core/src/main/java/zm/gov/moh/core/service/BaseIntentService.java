package zm.gov.moh.core.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.utils.ConcurrencyUtils;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.InjectorUtils;

public abstract class BaseIntentService extends IntentService implements InjectableViewModel {

    protected ServiceManager.Service mService;
    protected Bundle mBundle;
    protected LocalBroadcastManager mLocalBroadcastManager;
    protected Repository repository;
    protected Database db;

    public BaseIntentService(ServiceManager.Service service){
        super(service.toString());
        this.mService = service;
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    abstract protected void executeAsync();

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        mBundle = intent.getExtras();

        if(mBundle == null)
            mBundle = new Bundle();

        mBundle.putSerializable(Key.SERVICE, mService);

        InjectorUtils.provideRepository(this,getApplication());
        db = repository.getDatabase();

        ConcurrencyUtils.asyncRunnable(this::executeAsync,this::onError);
    }

    @Override
    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Repository getRepository() {
        return repository;
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

    public void onError(Throwable throwable){

        notifyInterrupted();
    }
}
