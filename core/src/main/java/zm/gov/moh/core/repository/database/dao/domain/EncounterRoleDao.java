package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import zm.gov.moh.core.repository.database.entity.domain.EncounterRole;

@Dao
public interface EncounterRoleDao {

    //gets all locations
    @Query("SELECT * FROM encounter_role WHERE encounter_role_id = :id")
    LiveData<EncounterRole> getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EncounterRole... encounterRoles);
}