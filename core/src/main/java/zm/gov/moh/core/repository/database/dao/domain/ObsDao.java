package zm.gov.moh.core.repository.database.dao.domain;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import zm.gov.moh.core.repository.database.entity.domain.Obs;

@Dao
public interface ObsDao {

    @Query("SELECT MAX(obs_id) FROM obs")
    Long getMaxId();

    // Inserts single getPersons
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Obs obs);

    // Inserts single getPersons
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Obs... obs);

    // Inserts single getPersons
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Obs> obs);

    //get getPersons by id
    @Query("SELECT * FROM obs WHERE person_id = :id")
    List<Obs> findByPatientId(long id);

    //get getPersons by id
    @Query("SELECT * FROM obs WHERE person_id = :id")
    List<Obs> getCodedValueByConceptId(long id);

    @Query("SELECT * FROM obs WHERE concept_id = :id")
    Obs findByConceptId(long id);


    //query to pick patient by conceptuuid
    @Query("SELECT * FROM obs WHERE concept_id=(SELECT concept_id FROM concept WHERE uuid = :conceptUuid) AND person_id = :patientId ORDER BY date_created DESC" )
    LiveData<Obs>findPatientObsByConceptUuid(long patientId, String conceptUuid);

    @Query("SELECT * FROM obs WHERE encounter_id = :id")
    List<Obs> getObsByEncountId(long id);

    @Query("SELECT * FROM obs WHERE encounter_id IN (SELECT encounter_id FROM encounter WHERE visit_id = :visitId) AND concept_id = :conceptId")
    List<Obs> getObsByVisitIdConceptId(Long visitId, Long conceptId);

    @Query("SELECT * FROM obs WHERE concept_id IN (:ids)")
    List<Obs> findByConceptId(long... ids);
}
