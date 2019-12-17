package zm.gov.moh.core.service.worker;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDateTime;
import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import zm.gov.moh.core.model.IntentAction;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.rest.RestApi;
import zm.gov.moh.core.repository.database.entity.SynchronizableEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;


public abstract class RemoteWorker extends BaseWorker {

    protected String accessToken ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7InV1aWQiOiJhM2FjYTE4MS1kMDJhLTRiODgtOTc1NC1lYWM0NWQzZGUzZmUiLCJkaXNwbGF5IjoiYW50aG9ueSIsInVzZXJuYW1lIjoiYW50aG9ueSIsInN5c3RlbUlkIjoiMy00In0sImlhdCI6MTU0MjE0MzU3NiwiZXhwIjoxNTkyMTQzNTc2fQ.DsDbPXwaZ5sg2SFCq1CBykITJjog-9u-4XzNGw9IYV8";
    protected final int TIMEOUT = 300000;
    protected RestApi restApi;
    protected long workerTimeout = 600000;
    protected int taskPoolSize = 0;

    long locationId;
    protected final int LIMIT = 100;
    protected LocalDateTime MIN_DATETIME = LocalDateTime.parse("1970-01-01T00:00:00");
    long OFFSET = 0;

    public RemoteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams){
        super(context, workerParams);
        AndroidThreeTen.init(context);
        workerTimeout += SystemClock.currentThreadTimeMillis();
        //TODO: replace hard coded token with dynamically assigned tokens
        //accessToken = getRepository().getDefaultSharePrefrences().getString(Key.ACCESS_TOKEN,null);
        if(accessToken == null){

            return;
        }

        restApi = repository.getRestApi();
        locationId = repository.getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0);
    }

    @NonNull
    @Override
    public Result doWork() {


        return super.doWork();
    }


    @Override
    public void onError(Throwable throwable) {
        super.onError(throwable);

        mLocalBroadcastManager.sendBroadcast(new Intent(IntentAction.REMOTE_SERVICE_INTERRUPTED));
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

    public void updateMetadata(SynchronizableEntity[] synchronizableEntities, EntityType entityType){

        LocalDateTime lastModified;
        for (SynchronizableEntity entity: synchronizableEntities){
            LocalDateTime dateCreated = entity.getDateCreated();
            LocalDateTime dateChanged = entity.getDateChanged();


            if(dateChanged != null)
                lastModified = dateChanged;
            else
                lastModified = dateCreated;

            db.entityMetadataDao().insert(new EntityMetadata(entity.getId(), entityType.getId(), Status.SYNCED.getCode(),lastModified));
        }
    }

    public void onTaskCompleted(){
        taskPoolSize--;
    }

    public Result awaitResult(){

        while (SystemClock.currentThreadTimeMillis() < workerTimeout && mResult.equals(Result.success())){
            if(this.taskPoolSize == 0)
                return mResult;
        }

        mLocalBroadcastManager.sendBroadcast(new Intent(IntentAction.REMOTE_SERVICE_INTERRUPTED));
        return Result.failure();

    }
}