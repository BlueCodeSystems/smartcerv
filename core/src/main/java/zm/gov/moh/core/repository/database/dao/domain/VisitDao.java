package zm.gov.moh.core.repository.database.dao.domain;

import org.threeten.bp.LocalDateTime;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;

@Dao
public interface VisitDao extends Synchronizable<Long> {

    @Query("SELECT MAX(visit_id) FROM Visit")
    Long getMaxId();

    @Query("SELECT visit_id FROM Visit")
    List<Long> getIds();

    //gets all locations
    @Query("SELECT * FROM Visit WHERE patient_id = :id")
    LiveData<List<VisitEntity>> getByPatientIdLive(long id);

    //gets all locations
    @Query("SELECT visit_id FROM Visit WHERE datetime(date_started) = datetime(:dateTime)")
    Long[] getByDatetime(LocalDateTime dateTime);

    @Query("SELECT * FROM Visit WHERE patient_id = :id")
    VisitEntity[] getByPatientId(long id);

    @Query("UPDATE Visit SET visit_id= :id, patient_id = :patientId WHERE date_started = :dateTime ")
    void updatePatientId(long id, long patientId, LocalDateTime dateTime);

    @Query("SELECT * FROM  Visit WHERE date_started = :dateTime AND visit_id >= :offset")
    VisitEntity getByDate( LocalDateTime dateTime, long offset);

    @Query("SELECT * FROM  Visit WHERE date_started >= :dateTime")
    VisitEntity getAll( LocalDateTime dateTime);

    @Query("SELECT * FROM  Visit WHERE visit_id >= :offset")
    VisitEntity getByDate( long offset);

    @Query("DELETE FROM Visit WHERE visit_id IN (:visitId) AND visit_id >= :offset")
    void deleteById(long[] visitId, long offset);

    @Query("SELECT * FROM Visit WHERE visit_id IN (:id)")
    List<VisitEntity> getById(Long[] id);

    @Query("SELECT date_started FROM Visit WHERE visit_id = :id")
    LocalDateTime getDateStartTimeByVisitId(long id);

    @Query("SELECT * FROM Visit WHERE visit_id = :id")
    VisitEntity getById(Long id);

    @Query("SELECT * FROM Visit WHERE visit_type_id IN (:visitTypes) AND patient_id = :id")
    LiveData<List<VisitEntity>> getByPatientIdVisitTypeId(long id, long... visitTypes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VisitEntity... visits);

    @Update
    void updateVisit(VisitEntity visit);

    @Override
    @Query("SELECT visit_id FROM (SELECT * FROM Visit WHERE visit_id NOT IN (:id)) WHERE visit_id >= :offsetId")
    Long[] findEntityNotWithId(long offsetId, long... id);

    @Query("UPDATE visit SET patient_id = :remotePatientId WHERE visit_id IN (SELECT visit_id FROM visit WHERE patient_id = :localPatientId)")
    void replaceLocalPatientId(long localPatientId, long remotePatientId);

}