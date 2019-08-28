package zm.gov.moh.core.service.worker;

import android.content.Context;
import android.content.Intent;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDateTime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.WorkerParameters;
import zm.gov.moh.core.model.IntentAction;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.rest.RestApi;
import zm.gov.moh.core.repository.database.entity.SynchronizableEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.BaseIntentService;
import zm.gov.moh.core.service.ServiceManager;


public abstract class RemoteWorker extends BaseWorker {

    protected String accessToken ="";
    protected final int TIMEOUT = 300000;
    protected int tasksCompleted = 0;
    protected int tasksStarted = 0;
    protected RestApi restApi;

    long locationId;
    protected final int LIMIT = 100;
    LocalDateTime lastModified;
    protected LocalDateTime MIN_DATETIME = LocalDateTime.parse("1970-01-01T00:00:00");
    long OFFSET = 0;

    public RemoteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams){
        super(context, workerParams);
        AndroidThreeTen.init(context);

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
}