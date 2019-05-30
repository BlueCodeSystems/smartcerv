package zm.gov.moh.core.repository.database.dao.system;

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EntityMetadata... entityMetadata);
}
