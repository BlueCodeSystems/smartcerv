package zm.gov.moh.core.repository.database.dao.domain;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.Patient;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;

@Dao
public interface PatientDao {

    //gets all persons attribute type
    @Query("SELECT * FROM patient")
    List<Patient> getAll();

    // Inserts single person name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Patient patient);

    // Inserts single person name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Patient... patients);

    //get persons name by person id
    @Query("SELECT * FROM patient WHERE patient_id = :id")
    Patient findById(long id);
}