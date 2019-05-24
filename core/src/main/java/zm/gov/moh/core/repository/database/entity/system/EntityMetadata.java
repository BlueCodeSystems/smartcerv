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
    @Json(name = "sync_status")
    private short syncStatus;

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

    public void setSyncStatus(short syncStatus) {
        this.syncStatus = syncStatus;
    }

    public short getSyncStatus() {
        return syncStatus;
    }
}