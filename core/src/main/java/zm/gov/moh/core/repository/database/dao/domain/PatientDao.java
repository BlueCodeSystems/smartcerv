package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.Patient;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;

@Dao
public interface PatientDao extends Synchronizable<Patient> {

    //gets all persons attribute type
    @Query("SELECT * FROM patient")
    List<Patient> getAll();

    // Inserts single getPersons name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Patient patient);

    // Inserts single getPersons name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Patient... patients);

    //get persons name by getPersons id
    @Query("SELECT * FROM patient WHERE patient_id = :id")
    Patient findById(long id);

    //get persons name by getPersons id
    @Query("SELECT MAX(patient_id) FROM patient")
    Long getMaxId();

    @Override
    @Query("SELECT * FROM (SELECT * FROM patient WHERE patient_id NOT IN (:id)) WHERE patient_id >= :offsetId")
    Patient[] findEntityNotWithId(long offsetId,long... id);
}