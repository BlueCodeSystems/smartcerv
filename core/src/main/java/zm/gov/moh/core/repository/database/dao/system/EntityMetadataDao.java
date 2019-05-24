package zm.gov.moh.core.repository.database.dao.system;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface EntityMetadataDao {

    @Query("SELECT DISTINCT entityId FROM entity_metadata WHERE entityTypeId = :entityTypeId AND syncStatus = :syncStatus" )
    long[] findEntityIdByTypeSyncStatus(int entityTypeId, short syncStatus);
}
