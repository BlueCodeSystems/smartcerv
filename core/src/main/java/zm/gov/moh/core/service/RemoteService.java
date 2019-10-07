package zm.gov.moh.core.service;

import android.content.Intent;

import androidx.annotation.Nullable;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDateTime;

import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.rest.RestApi;
import zm.gov.moh.core.repository.database.entity.SynchronizableEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;

@Deprecated
public abstract class RemoteService extends BaseIntentService {

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
        locationId = repository.getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0);
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