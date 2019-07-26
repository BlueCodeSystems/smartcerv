package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierType;

@Dao
public interface PatientIdentifierTypeDao {

    //gets all persons attribute type
    @Query("SELECT * FROM patient_identifier_type")
    List<PatientIdentifierType> getAll();

    // Inserts single getPersons name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PatientIdentifierType patientIdentifierType);

    // Inserts single getPersons name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PatientIdentifierType... patientIdentifierTypes);

    //get persons name by getPersons id
    @Query("SELECT * FROM patient_identifier_type WHERE patient_identifier_type_id = :id")
    PatientIdentifierType findById(long id);
}
