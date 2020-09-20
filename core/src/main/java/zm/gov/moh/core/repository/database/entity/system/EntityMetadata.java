package zm.gov.moh.core.repository.database.entity.system;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import zm.gov.moh.core.model.Visit;

@Entity(tableName = "entity_metadata")
public class EntityMetadata {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @Json(name = "entity_id")
    @ColumnInfo(name = "entity_id")
    private long entityId;

    @Json(name = "entity_type_id")
    @ColumnInfo(name = "entity_type_id")
    private int entityTypeId;

    @Json(name = "remote_status_code")
    @ColumnInfo(name = "remote_status_code")
    private short remoteStatusCode;

    @Json(name = "last_modified")
    @ColumnInfo(name = "last_modified")
    private LocalDateTime lastModified;

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

    public short getRemoteStatusCode() {
        return remoteStatusCode;
    }

    public void setRemoteStatusCode(short remoteStatusCode) {
        this.remoteStatusCode = remoteStatusCode;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    @Ignore
    public EntityMetadata(long entityId, int entityTypeId, short remoteStatusCode){
        this.entityId = entityId;
        this.entityTypeId = entityTypeId;
        this.remoteStatusCode = remoteStatusCode;
    }

    public EntityMetadata(long entityId, int entityTypeId){
        this.entityId = entityId;
        this.entityTypeId = entityTypeId;
    }

    @Ignore
    public EntityMetadata(long entityId, int entityTypeId, short remoteStatusCode, LocalDateTime lastModified){
        this.entityId = entityId;
        this.entityTypeId = entityTypeId;
        this.remoteStatusCode = remoteStatusCode;
        this.lastModified = lastModified;
    }

    public void persist(Visit any) {
    }
}