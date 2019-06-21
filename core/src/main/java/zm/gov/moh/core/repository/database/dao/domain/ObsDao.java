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
    List<ObsEntity> findByPatientId(long id);

    //get getPersons by id
    @Query("SELECT * FROM obs WHERE person_id = :id")
    List<ObsEntity> getCodedValueByConceptId(long id);

    @Query("SELECT * FROM obs WHERE concept_id = :id")
    ObsEntity findByConceptId(long id);


    //query to pick patient by conceptuuid
    @Query("SELECT * FROM obs WHERE concept_id=(SELECT concept_id FROM concept WHERE uuid = :conceptUuid) AND person_id = :patientId ORDER BY date_created DESC" )
    LiveData<ObsEntity>findPatientObsByConceptUuid(long patientId, String conceptUuid);

    @Query("SELECT * FROM obs WHERE encounter_id = :id")
    List<ObsEntity> getObsByEncounterId(long id);

    @Query("SELECT * FROM obs WHERE encounter_id IN (:encounterIds)")
    ObsEntity[] getObsByEncounterId(Long[] encounterIds);

    @Query("SELECT * FROM obs WHERE encounter_id IN (SELECT encounter_id FROM encounter WHERE visit_id = :visitId) AND concept_id = :conceptId")
    List<ObsEntity> getObsByVisitIdConceptId(Long visitId, Long conceptId);

    @Query("SELECT * FROM obs WHERE concept_id IN (:ids)")
    List<ObsEntity> findByConceptId(long... ids);

    @Override
    @Query("SELECT * FROM (SELECT * FROM obs WHERE obs_id NOT IN (:id)) WHERE obs_id >= :offsetId")
    ObsEntity[] findEntityNotWithId(long offsetId, long... id);
}
