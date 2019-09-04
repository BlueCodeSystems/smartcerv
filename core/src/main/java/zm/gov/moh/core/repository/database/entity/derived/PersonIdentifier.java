package zm.gov.moh.core.repository.database.entity.derived;

import com.squareup.moshi.Json;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import zm.gov.moh.core.repository.database.entity.domain.Person;

@Entity(tableName = "person_identifier")
public class PersonIdentifier {

    @PrimaryKey
    @NonNull
    private String identifier;

    @ColumnInfo(name = "remote_id")
    @Json(name = "remote_id")
    private Long remoteId;

    @ColumnInfo(name = "local_id")
    @Json(name = "local_id")
    private Long localId;

    @ColumnInfo(name = "remote_uuid")
    @Json(name = "remote_uuid")
    private String remoteUuid;

    @NonNull
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(@NonNull String identifier) {
        this.identifier = identifier;
    }

    public Long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(Long remoteId) {
        this.remoteId = remoteId;
    }

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    public String getRemoteUuid() {
        return remoteUuid;
    }

    public void setRemoteUuid(String remoteUuid) {
        this.remoteUuid = remoteUuid;
    }

    public PersonIdentifier(String identifier, Long localId){
        this.identifier = identifier;
        this.localId = localId;
    }
}
