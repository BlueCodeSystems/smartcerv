package zm.gov.moh.core.repository.database.dao.domain;

import java.util.LinkedList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.Encounter;

@Dao
public interface EncounterDao extends Synchronizable<Encounter> {

    @Query("SELECT MAX(encounter_id) FROM encounter")
    Long getMaxId();
    //get by patient and encouter type
    @Query("SELECT * FROM encounter WHERE patient_id = :id AND encounter_type = :encounterTypeId")
    LiveData<Encounter> getByPatientId(long id, long encounterTypeId);

    //gets all locations
    @Query("SELECT * FROM encounter WHERE patient_id = :id")
    LiveData<Encounter> getByPatientId(long id);

    //gets all locations
    @Query("SELECT * FROM encounter WHERE visit_id = :visitId ORDER BY encounter_type")
    List<Encounter> getByEncounterByVisitId(long visitId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Encounter... encounters);

    @Override
    @Query("SELECT * FROM encounter WHERE encounter_id NOT IN (:id)")
    Encounter[] findEntityNotWithId(long... id);
}
