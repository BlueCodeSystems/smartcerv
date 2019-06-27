package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;
import java.util.List;

import zm.gov.moh.core.model.PatientIdentifier;
import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;

@Dao
public interface PatientIdentifierDao extends Synchronizable<PatientIdentifierEntity> {

    //gets all persons attribute type
    @Query("SELECT * FROM patient_identifier")
    List<PatientIdentifierEntity> getAll();

    // Inserts single getPersons name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PatientIdentifierEntity patientIdentifier);

    // Inserts single getPersons name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PatientIdentifierEntity... patientIdentifiers);

    //get persons name by getPersons id
    @Query("DELETE FROM patient_identifier WHERE patient_identifier_id IN (:id)")
     void deleteByPatientId(long ...id);

    @Query("SELECT identifier,patient_identifier_type.uuid AS identifierType, location.uuid AS location, preferred FROM patient_identifier JOIN patient_identifier_type ON patient_identifier.identifier_type = patient_identifier_type.patient_identifier_type_id JOIN location ON patient_identifier.location_id = location.location_id WHERE patient_id = :id AND patient_identifier.voided = 0")
    List<PatientIdentifier> findAllByPatientId(long id);

    //get persons name by getPersons id
    @Query("SELECT MAX(patient_identifier_id) FROM patient_identifier")
    Long getMaxId();

    @Query("SELECT patient_identifier_id FROM patient_identifier WHERE uuid = :uuid")
    Long getIdByUuid(String uuid);

    @Query("SELECT local.patient_identifier_id FROM (SELECT patient_identifier_id, identifier FROM patient_identifier WHERE patient_identifier_id < :localOffset) AS remote JOIN (SELECT patient_identifier_id, identifier FROM patient_identifier WHERE patient_identifier_id >= :localOffset) AS local ON local.identifier = remote.identifier")
    long[] getSynced(long localOffset);

    @Override
    @Query("SELECT * FROM (SELECT * FROM patient_identifier WHERE patient_identifier_id NOT IN (:id)) WHERE patient_identifier_id >= :offsetId")
    PatientIdentifierEntity[] findEntityNotWithId(long offsetId, long... id);
}
