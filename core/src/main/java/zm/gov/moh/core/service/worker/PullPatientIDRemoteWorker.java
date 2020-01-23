package zm.gov.moh.core.service.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import zm.gov.moh.core.BuildConfig;
import zm.gov.moh.core.repository.database.entity.custom.Identifier;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class PullPatientIDRemoteWorker extends RemoteWorker {


    final int SOURCE = BuildConfig.REMOTE_ID_SOURCE;
    final int BATCH_SIZE = BuildConfig.REMOTE_ID_BATCH_SIZE;
    final int RESERVED = BuildConfig.REMOTE_ID_RESERVE;
    final short NOT_ASSIGNED = 0;


    public PullPatientIDRemoteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams){
        super(context, workerParams);
    }

    @Override
    @NonNull
    public Result doWork() {

        int identifiersNotAssigned = db.identifierDao().count(NOT_ASSIGNED);

        if(RESERVED > identifiersNotAssigned) {

            ConcurrencyUtils.consumeBlocking(identifierList -> {

                        for (String identifier : identifierList.getIdentifiers())
                            db.identifierDao().insert(new Identifier(identifier));

                    }, this::onError,
                    restApi.getIdentifiers(accessToken, SOURCE, BATCH_SIZE),
                    TIMEOUT);
            return mResult;
        }
        else
           return mResult;
    }
}
