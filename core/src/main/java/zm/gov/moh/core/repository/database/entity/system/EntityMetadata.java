package zm.gov.moh.core.repository.database.entity.system;

import com.squareup.moshi.Json;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "entity_metadata")
public class EntityMetadata {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @Json(name = "entity_id")
    private long entityId;
    @Json(name = "entity_type_id")
    private int entityTypeId;
    @Json(name = "remote_status")
    private short remoteStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getEntityTypeId() {
        return entityTypeId;
    }

    public void setEntityTypeId(int entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public short getRemoteStatus() {
        return remoteStatus;
    }

    public void setRemoteStatus(short remoteStatus) {
        this.remoteStatus = remoteStatus;
    }

    public EntityMetadata(long entityId, int entityTypeId, short remoteStatus){
        this.entityId = entityId;
        this.entityTypeId = entityTypeId;
        this.remoteStatus = remoteStatus;
    }
}