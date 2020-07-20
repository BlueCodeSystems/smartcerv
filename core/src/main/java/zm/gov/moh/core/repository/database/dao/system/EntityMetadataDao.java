package zm.gov.moh.core.repository.database.dao.system;

import org.threeten.bp.LocalDateTime;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;

@Dao
public interface EntityMetadataDao {

    @Query("SELECT DISTINCT entity_id FROM entity_metadata WHERE entity_type_id = :entityTypeId AND remote_status_code = :remoteStatus")
    long[] findEntityIdByTypeRemoteStatus(int entityTypeId, short remoteStatus);

    @Query("SELECT entity_id FROM entity_metadata WHERE remote_status_code = :remoteStatus")
    Long[] findByStatus(short remoteStatus);

    @Query("SELECT DISTINCT * FROM entity_metadata WHERE entity_type_id = :entityTypeId AND remote_status_code = :remoteStatus")
    EntityMetadata[] findEntityIdByTypeRemoteStatus2(int entityTypeId, short remoteStatus);

    @Query("UPDATE entity_metadata SET last_modified = :lastModifiedDate WHERE entity_id = :entityId")
    void updateLastModifiedDate(long entityId, LocalDateTime lastModifiedDate);

    @Query("SELECT * FROM entity_metadata WHERE entity_id = :entityId")
    EntityMetadata findEntityById(long entityId);

    @Query("SELECT DISTINCT entity_id FROM entity_metadata WHERE entity_type_id = :entityTypeId AND remote_status_code = :remoteStatus AND last_modified >= :lastModified")
    long[] findEntityIdByTypeRemoteStatus(int entityTypeId, short remoteStatus, LocalDateTime lastModified);

    @Query("SELECT MAX(last_modified) FROM entity_metadata WHERE entity_type_id = :entityTypeId")
    LocalDateTime findEntityLatestModifiedById(int entityTypeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EntityMetadata... entityMetadata);

}
