package zm.gov.moh.core.repository.database.dao.domain;

import org.threeten.bp.LocalDateTime;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;

@Dao
public interface EncounterDao extends Synchronizable<EncounterEntity> {

    @Query("SELECT encounter_id FROM encounter")
    List<Long> getIds();

    @Query("SELECT MAX(encounter_id) FROM encounter")
    Long getMaxId();
    //get by patient and encouter type
    @Query("SELECT * FROM encounter WHERE patient_id = :id AND encounter_type = :encounterTypeId")
    LiveData<EncounterEntity> getByPatientId(long id, long encounterTypeId);

    @Query("DELETE FROM encounter WHERE encounter_id IN (:encounterId) AND encounter_id >= :offset")
    void deleteById(long[] encounterId, long offset);

    @Query("SELECT encounter_id FROM encounter WHERE datetime(encounter_datetime) = datetime(:dateTime)")
    Long[] getByDatetime(LocalDateTime dateTime);

    @Query("SELECT * FROM encounter WHERE visit_id IN (:visitId) AND voided != 1")
    List<EncounterEntity> getByVisitId(List<Long> visitId);

    @Query("SELECT * FROM encounter WHERE visit_id =:visitId AND encounter_type = :encounterTypeId ORDER BY date_created DESC LIMIT 1")
    EncounterEntity getByTypeVisitId(Long visitId, Long encounterTypeId);

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
    @Query("SELECT * FROM encounter WHERE visit_id = :visitId AND voided != 1 ORDER BY encounter_type")
    List<EncounterEntity> getByEncounterByVisitId(long visitId);

    @Query("UPDATE encounter SET encounter_id= :id, patient_id = :patientId WHERE encounter_datetime = :dateTime ")
    void updateEncounter(long id, long patientId, LocalDateTime dateTime);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EncounterEntity... encounters);

    @Override
    @Query("SELECT * FROM (SELECT * FROM encounter WHERE encounter_id NOT IN (:id)) WHERE encounter_id >= :offsetId")
    EncounterEntity[] findEntityNotWithId(long offsetId, long... id);
}
