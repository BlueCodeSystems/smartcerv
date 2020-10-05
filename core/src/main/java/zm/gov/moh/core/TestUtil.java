package zm.gov.moh.core;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import zm.gov.moh.core.model.UserMock;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.service.worker.BaseWorker;


class TestUtil extends BaseWorker {

    public Repository mRepository;
    public Database database;

    public TestUtil(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public static UserMock createUser(int i) {
        return null;
    }


    public Repository getRepo()
    {
        mRepository = getRepository();
        return mRepository;
    }

    public Database getDb()
    {
        database =mRepository.getDatabase();
        return database;
    }



    public void setPatient(String ju) {
    }
}