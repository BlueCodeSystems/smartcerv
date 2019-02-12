package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.EncounterRole;
import zm.gov.moh.core.repository.database.entity.domain.EncounterType;

@Dao
public interface EncounterTypeDao {

    //gets all locations
    @Query("SELECT * FROM encounter_type WHERE encounter_type_id = :id")
    LiveData<EncounterType> getById(long id);

    @Query("SELECT encounter_type_id FROM encounter_type WHERE uuid = :uuid")
    Long getIdByUuid(String uuid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EncounterType... encounterTypes);
}