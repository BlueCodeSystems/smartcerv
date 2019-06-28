package zm.gov.moh.core.repository.database.dao.domain;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;

@Dao
public interface EncounterDao extends Synchronizable<EncounterEntity> {

    @Query("SELECT MAX(encounter_id) FROM encounter")
    Long getMaxId();
    //get by patient and encouter type
    @Query("SELECT * FROM encounter WHERE patient_id = :id AND encounter_type = :encounterTypeId")
    LiveData<EncounterEntity> getByPatientId(long id, long encounterTypeId);

    @Query("DELETE FROM encounter WHERE encounter_id IN (:encounterId)")
    void deleteById(long[] encounterId);

    @Query("SELECT * FROM encounter WHERE visit_id IN (:visitId)")
    List<EncounterEntity> getByVisitId(List<Long> visitId);

    @Query("SELECT encounter_id FROM encounter WHERE visit_id IN (:visitId)")
    long[] getByVisitId(long[] visitId);

    //gets all locations
    @Query("SELECT * FROM encounter WHERE patient_id = :id")
    LiveData<EncounterEntity> getByPatientId(long id);

    @Query("SELECT * FROM encounter WHERE patient_id = :id")
    EncounterEntity[] getByPatientIdT(long id);

    @Query("UPDATE encounter SET patient_id = :remotePatientId WHERE patient_id = :localPatientId")
    void replaceLocalPatientId(long localPatientId, long remotePatientId);

    //gets all locations
    @Query("SELECT * FROM encounter WHERE visit_id = :visitId ORDER BY encounter_type")
    List<EncounterEntity> getByEncounterByVisitId(long visitId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EncounterEntity... encounters);

    @Override
    @Query("SELECT * FROM (SELECT * FROM encounter WHERE encounter_id NOT IN (:id)) WHERE encounter_id >= :offsetId")
    EncounterEntity[] findEntityNotWithId(long offsetId, long... id);
}
