package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.LinkedList;
import java.util.List;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.Obs;

@Dao
public interface ObsDao extends Synchronizable<Obs> {

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

    @Query("SELECT * FROM obs WHERE encounter_id = :id")
    List<Obs> getObsByEncountId(long id);

    @Query("SELECT * FROM obs WHERE encounter_id IN (SELECT encounter_id FROM encounter WHERE visit_id = :visitId) AND concept_id = :conceptId")
    List<Obs> getObsByVisitIdConceptId(Long visitId, Long conceptId);

    @Query("SELECT * FROM obs WHERE concept_id IN (:ids)")
    List<Obs> findByConceptId(long... ids);

    @Override
    @Query("SELECT * FROM obs WHERE obs_id NOT IN (:id)")
    Obs[] findEntityNotWithId(long... id);
}
