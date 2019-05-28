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

    @Query("SELECT DISTINCT entityId FROM entity_metadata WHERE entityTypeId = :entityTypeId AND remoteStatus = :remoteStatus")
    long[] findEntityIdByTypeRemoteStatus(int entityTypeId, short remoteStatus);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EntityMetadata... entityMetadata);
}
