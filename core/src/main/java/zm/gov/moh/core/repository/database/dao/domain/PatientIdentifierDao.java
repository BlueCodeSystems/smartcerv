package zm.gov.moh.core.repository.database.dao.domain;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.Patient;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifier;

@Dao
public interface PatientIdentifierDao {

    //gets all persons attribute type
    @Query("SELECT * FROM patient_identifier")
    List<PatientIdentifier> getAll();

    // Inserts single person name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PatientIdentifier patientIdentifier);

    // Inserts single person name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PatientIdentifier... patientIdentifiers);

    //get persons name by person id
    @Query("SELECT * FROM patient_identifier WHERE patient_id = :id")
    PatientIdentifier findByPatientId(long id);
}
