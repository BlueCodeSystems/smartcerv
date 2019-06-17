package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.PatientEntity;

@Dao
public interface PatientDao extends Synchronizable<Long> {

    //gets all persons attribute type
    @Query("SELECT * FROM patient")
    List<PatientEntity> getAll();

    // Inserts single getPersons name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PatientEntity patient);

    // Inserts single getPersons name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PatientEntity... patients);

    //get persons name by getPersons id
    @Query("SELECT * FROM patient WHERE patient_id = :id")
    PatientEntity findById(long id);

    @Query("SELECT patient_id FROM patient")
    List <Long> findByAllPatientId();

    //get persons name by getPersons id
    @Query("SELECT MAX(patient_id) FROM patient")
    Long getMaxId();

    @Override
    @Query("SELECT patient_id FROM (SELECT * FROM patient WHERE patient_id NOT IN (:id)) WHERE patient_id >= :offsetId")
    Long[] findEntityNotWithId(long offsetId, long... id);
}