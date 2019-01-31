package zm.gov.moh.core.repository.database.dao.derived;

import androidx.lifecycle.LiveData;
import androidx.room.*;


import java.util.List;

import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.repository.database.entity.domain.Encounter;
import zm.gov.moh.core.repository.database.entity.domain.Obs;
import zm.gov.moh.core.repository.database.entity.domain.Visit;

@Dao
public interface GenericDao {

    @Query("SELECT COUNT(DISTINCT patient_id) AS count from patient_identifier WHERE identifier_type=4 AND location_id = :id")
    LiveData<Long> countPatientsByLocationId(Long id);

    @Query("SELECT patient.patient_id,patient_identifier.identifier, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 4")
    LiveData<List<Client>> getAllPatients();

    @Query("SELECT patient.patient_id,patient_identifier.identifier, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 4 AND location_id = :id")
    LiveData<List<Client>> getAllPatientsByLocation(long id);

    @Query("SELECT patient.patient_id,patient_identifier.identifier, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 4 AND patient.patient_id = :id")
    LiveData<Client> getPatientById(long id);

    //get getPersons by id
    @Query("SELECT * FROM obs WHERE encounter_id IN (SELECT encounter_id FROM encounter WHERE visit_id = :visitId AND encounter_type = (SELECT encounter_type_id  FROM encounter_type WHERE uuid = :encounterTypeUuid) AND patient_id = :patientId)")
    List<Obs> getPatientObsByEncounterTypeAndVisitId(long patientId, long visitId, String encounterTypeUuid);

    //get getPersons by id
    @Query("SELECT value_coded FROM obs WHERE encounter_id = :encounterId AND person_id = :patientId AND concept_id = :conceptId")
    Long getPatientObsCodedValueByEncounterIdConceptId(long patientId, long conceptId, long encounterId);

    //get getPersons by id =9223372036854725812
    @Query("SELECT * FROM obs Where encounter_id in (select encounter_id from encounter where encounter_type = 12)")
    List<Obs> testQ();

}