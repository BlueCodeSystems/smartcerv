package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;
import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.Patient;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifier;

@Dao
public interface PatientIdentifierDao {

    //gets all persons attribute type
    @Query("SELECT * FROM patient_identifier")
    List<PatientIdentifier> getAll();

    // Inserts single getPersons name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PatientIdentifier patientIdentifier);

    // Inserts single getPersons name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PatientIdentifier... patientIdentifiers);

    //get persons name by getPersons id
    @Query("SELECT * FROM patient_identifier WHERE patient_id = :id")
    PatientIdentifier findByPatientId(long id);

    //get persons name by getPersons id
    @Query("SELECT MAX(patient_identifier_id) FROM patient_identifier")
    Long getMaxId();

    @Query("SELECT patient_identifier_id FROM patient_identifier WHERE uuid = :uuid")
    Long getIdByUuid(String uuid);
}
