package zm.gov.moh.core.repository.database.dao.domain;

import java.util.List;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import zm.gov.moh.core.repository.database.entity.domain.ObsEntity;

@Dao
public interface ObsDao extends Synchronizable<ObsEntity> {

    @Query("SELECT MAX(obs_id) FROM obs")
    Long getMaxId();

    @Query("SELECT obs_id FROM obs")
    List<Long> getIds();

    // Inserts single getPersons
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ObsEntity obs);

    // Inserts single getPersons
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ObsEntity... obs);

    // Inserts single getPersons
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ObsEntity> obs);

    //get getPersons by id
    @Query("SELECT * FROM obs WHERE person_id = :id")
    ObsEntity[] findByPatientId(long id);

    @Query("DELETE FROM obs WHERE obs_id IN (:obsId) AND obs_id >= :offset")
    void deleteById(long[] obsId, long offset);

    //get getPersons by id
    @Query("SELECT * FROM obs WHERE person_id = :id")
    List<ObsEntity> getCodedValueByConceptId(long id);

    @Query("SELECT * FROM obs WHERE concept_id = :id")
    ObsEntity findByConceptId(long id);

    //query to pick patient by conceptuuid
    @Query("SELECT * FROM obs WHERE concept_id = (SELECT concept_id FROM concept WHERE uuid = :conceptUuid) AND encounter_id IN (SELECT encounter_id FROM encounter WHERE visit_id = :visitId GROUP BY encounter_type ORDER BY date_created DESC) ORDER BY date_created DESC" )
    LiveData<ObsEntity[]>findPatientObsByConceptUuid(long visitId, String conceptUuid);

    @Query("SELECT * FROM obs WHERE encounter_id = :id AND voided != 1")
    List<ObsEntity> getObsByEncounterId(long id);

    @Query("SELECT * FROM obs WHERE obs_id = :id")
    ObsEntity getObsById(long id);

    @Query("SELECT obs_id FROM obs WHERE encounter_id IN (:encounterId)")
    long[] getObsByEncounterId(long[] encounterId );

    @Query("SELECT * FROM obs WHERE encounter_id IN (:encounterIds)")
    List<ObsEntity> getObsByEncounterId(List<Long> encounterIds);

    @Query("SELECT * FROM obs WHERE encounter_id IN (SELECT encounter_id FROM encounter WHERE visit_id = :visitId) AND concept_id = :conceptId")
    List<ObsEntity> getObsByVisitIdConceptId(Long visitId, Long conceptId);

    @Query("SELECT * FROM obs WHERE concept_id IN (:ids)")
    List<ObsEntity> findByConceptId(long... ids);

    @Override
    @Query("SELECT * FROM (SELECT * FROM obs WHERE obs_id NOT IN (:id)) WHERE obs_id >= :offsetId")
    ObsEntity[] findEntityNotWithId(long offsetId, long... id);

    @Query("UPDATE obs SET person_id = :remotePersonId WHERE person_id = :localPersonId")
    void replaceLocalPersonId(long localPersonId, long remotePersonId);
}
