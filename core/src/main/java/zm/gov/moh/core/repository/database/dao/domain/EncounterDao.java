package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.Encounter;

@Dao
public interface EncounterDao {

    //get by patient and encouter type
    @Query("SELECT * FROM encounter WHERE patient_id = :id AND encounter_type = :encounterTypeId")
    LiveData<Encounter> getByPatientId(long id, long encounterTypeId);

    //gets all locations
    @Query("SELECT * FROM encounter WHERE patient_id = :id")
    LiveData<Encounter> getByPatientId(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Encounter... encounters);
}
