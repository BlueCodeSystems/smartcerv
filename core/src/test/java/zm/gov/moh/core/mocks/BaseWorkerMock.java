package zm.gov.moh.core.mocks;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.service.ServiceManager;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.InjectorUtils;

public abstract class BaseWorkerMock extends Worker implements InjectableViewModel {

    protected ServiceManager.Service mService;
    protected Bundle mBundle;
    protected LocalBroadcastManager mLocalBroadcastManager;
    protected Repository repository;
    public static Database db;
    protected Result mResult = Result.success();

    public BaseWorkerMock(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        mBundle = new Bundle();

        if (mBundle == null)
            mBundle = new Bundle();

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(context);

        mBundle.putSerializable(Key.SERVICE, mService);

        InjectorUtils.provideRepository(this, getApplicationContext());
        db = repository.getDatabase();
    }

    @NonNull
    @Override
    public Result doWork() {

        return null;
    }


    @Override
    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Repository getRepository() {
        return repository;
    }


    public void onError(Throwable throwable) {
        this.mResult = Result.failure();
    }
}
