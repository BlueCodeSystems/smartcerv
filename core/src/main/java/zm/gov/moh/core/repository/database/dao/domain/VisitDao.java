package zm.gov.moh.core.repository.database.dao.domain;

import org.threeten.bp.LocalDateTime;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;

@Dao
public interface VisitDao extends Synchronizable<Long> {

    @Query("SELECT visit_id FROM Visit")
    Long[] getAllVisitIDs();


    @Query("SELECT MAX(datetime) AS datetime FROM (SELECT CASE WHEN COALESCE(date_created,'1970-01-01T00:00:00') >= COALESCE(date_changed,'1970-01-01T00:00:00') THEN date_created ELSE date_changed END datetime FROM visit WHERE patient_id IN (SELECT DISTINCT patient_id FROM patient_identifier WHERE location_id = :locationId) AND uuid IS NOT NULL)")
    LocalDateTime getMaxDatetime(long locationId);

    @Query("SELECT MAX(visit_id) FROM Visit")
    Long getMaxId();

    @Query("SELECT visit_id FROM Visit")
    List<Long> getIds();

    //gets all locations
    @Query("SELECT * FROM Visit WHERE patient_id = :id")
    LiveData<List<VisitEntity>> getByPatientIdLive(long id);

    //gets all locations
    @Query("SELECT visit_id FROM Visit WHERE strftime('%s', date_started) = strftime('%s', :dateTime) AND location_id = :locationId")
    Long[] getByDatetime(String dateTime, long locationId);

    @Query("SELECT * FROM Visit WHERE patient_id = :id")
    VisitEntity[] getByPatientId(long id);

    @Query("UPDATE Visit SET visit_id= :id, patient_id = :patientId WHERE date_started = :dateTime ")
    void updatePatientId(long id, long patientId, LocalDateTime dateTime);

    @Query("SELECT * FROM  Visit WHERE date_started = :dateTime AND visit_id >= :offset")
    VisitEntity getByDate( LocalDateTime dateTime, long offset);

    @Query("SELECT * FROM  Visit WHERE date_started >= :dateTime")
    VisitEntity getAll( LocalDateTime dateTime);

    @Query("SELECT * FROM  Visit Order by date_started DESC")
    List<VisitEntity> getAll();

    @Query("SELECT * FROM  Visit WHERE visit_id >= :offset")
    VisitEntity getByDate( long offset);

    @Query("DELETE FROM Visit WHERE visit_id IN (:visitId) AND visit_id >= :offset")
    void deleteById(long[] visitId, long offset);

    @Query("SELECT * FROM Visit WHERE visit_id IN (:id) AND voided=0 ORDER BY visit_id LIMIT 900")
    List<VisitEntity> getById(Long[] id);

    //@Query("SELECT visit_id, patient_id, visit_type_id, voided FROM (SELECT * FROM Visit) WHERE visit_id NOT IN (:id) AND voided=0")
    //List<VisitEntity> getById(Long[] id);

    @Query("SELECT date_started FROM Visit WHERE visit_id = :id")
    LocalDateTime getDateStartTimeByVisitId(long id);

    @Query("SELECT * FROM Visit WHERE visit_id = :id")
    VisitEntity getById(Long id);

    @Query("SELECT * FROM Visit WHERE visit_type_id IN (:visitTypes) AND patient_id = :id AND voided != 1")
    LiveData<List<VisitEntity>> getByPatientIdVisitTypeId(long id, long... visitTypes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VisitEntity... visits);

    @Update
    void updateVisit(VisitEntity visit);

    @Override
    @Query("SELECT visit_id FROM (SELECT * FROM Visit WHERE visit_id NOT IN (:id)) WHERE visit_id >= :offsetId")
    Long[] findEntityNotWithId(long offsetId, long... id);

    @Query("SELECT visit_id FROM (SELECT * FROM Visit WHERE visit_id NOT IN (:id)) WHERE visit_id >= :offsetId ORDER by visit_id DESC LIMIT 10")
    Long[] findEntityWithId(long offsetId, long... id);


    //@Query("SELECT visit_id FROM Visit WHERE visit_id IN (:id) AND visit_id >= :offsetId")
    //Long[] findEntityWithId(long offsetId, long... id);

    @Query("UPDATE visit SET patient_id = :remotePatientId WHERE visit_id IN (SELECT visit_id FROM visit WHERE patient_id = :localPatientId)")
    void replaceLocalPatientId(long localPatientId, long remotePatientId);

    //void encounters
    @Query("Update encounter SET voided=1 WHERE visit_id=:visitId AND location_id=:locationId")
    void abortVisitEncounter(long visitId,long locationId);

    //void visit
    @Query("Update visit SET voided=1 WHERE visit_id=:visitId AND location_id=:locationId")
    void abortVisit(long visitId,long locationId);

    //void obs by visit id

    @Query("Update obs SET voided=1 WHERE obs.person_id IN(Select obs.person_id from obs join encounter on obs.encounter_id=encounter.encounter_id WHERE encounter.visit_id=:visitId) AND location_id=:locationId")
    void abortVisitObs(long visitId,long locationId);
}

