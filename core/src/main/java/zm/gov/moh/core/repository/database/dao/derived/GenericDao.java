package zm.gov.moh.core.repository.database.dao.derived;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.repository.database.entity.domain.ObsEntity;

@Dao
public  interface GenericDao {

    @Query("SELECT COUNT(DISTINCT patient_id) AS count from patient_identifier WHERE identifier_type=4 AND location_id = :id")
    LiveData<Long> countPatientsByLocationId(Long id);

    @Query("SELECT patient.patient_id,patient_identifier.identifier, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 4")
    LiveData<List<Client>> getAllPatients();

    @Query("SELECT  patient.patient_id,patient_identifier.identifier, patient_identifier.date_created, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN (SELECT *, max(date_created) FROM patient_identifier WHERE identifier_type = 4 GROUP BY patient_id) patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND location_id = :id AND patient_identifier.voided=0 ORDER BY patient_identifier.date_created DESC")
    LiveData<List<Client>> getAllPatientsByLocation(long id);

   // @Query("SELECT patient.patient_id,patient_identifier.identifier, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN (select distinct identifier, MAX(date_created) date_created, patient_id, identifier_type, location_id, voided FROM patient_identifier GROUP BY patient_id,identifier,identifier_type,location_id,voided)patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 4 AND location_id = :locationId AND patient.patient_id IN (:ids) AND (patient_identifier.voided != 1) ORDER BY patient_identifier.date_created DESC")

    @Query("SELECT patient.patient_id,patient_identifier.identifier, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 4 AND location_id = :locationId AND patient.patient_id IN (:ids) AND patient_identifier.voided=0 ORDER BY patient_identifier.date_created DESC")
    LiveData<List<Client>> getPatientsByLocation(long locationId, List<Long> ids);

    @Query("SELECT patient.patient_id,patient_identifier.identifier, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN (SELECT *, max(date_created) FROM patient_identifier WHERE identifier_type = 4 GROUP BY patient_id)patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND patient_identifier.voided !=1 AND identifier_type = 4 AND patient.patient_id = :id")
    LiveData<Client> getPatientById(long id);

    //get getPersons by id
    @Query("SELECT * FROM obs WHERE encounter_id IN (SELECT encounter_id FROM encounter WHERE visit_id = :visitId AND encounter_type = (SELECT encounter_type_id  FROM encounter_type WHERE uuid = :encounterTypeUuid) AND patient_id = :patientId)")
    List<ObsEntity> getPatientObsByEncounterTypeAndVisitId(long patientId, long visitId, String encounterTypeUuid);

    //get getPersons by id
    @Query("SELECT value_coded FROM obs WHERE encounter_id = :encounterId AND person_id = :patientId AND concept_id = :conceptId")
    Long getPatientObsCodedValueByEncounterIdConceptId(long patientId, long conceptId, long encounterId);

    //get getPersons by id =9223372036854725812
    @Query("SELECT * FROM obs Where encounter_id in (select encounter_id from encounter where encounter_type = 12)")
    List<ObsEntity> testQ();

    //get getPersons by id
    @Query("SELECT value_coded FROM obs JOIN encounter ON obs.encounter_id = encounter.encounter_id JOIN visit ON encounter.visit_id = visit.visit_id WHERE person_id = :patientId AND concept_id = :conceptId AND visit.visit_id = :visitId")
    List<Long> getPatientObsCodedValueByVisitIdConceptId(long patientId, long visitId, long conceptId);

    //get getPersons by id
    @Query("SELECT encounter_id FROM encounter WHERE encounter_type = (SELECT encounter_type_id FROM encounter_type WHERE uuid = :encounterTypeUuid) AND visit_id = :visitId AND patient_id = :patientId")
    Long getPatientEncounterIdByVisitIdEncounterTypeId(long patientId, long visitId, String encounterTypeUuid);

    @Query("SELECT * FROM obs WHERE encounter_id IN (SELECT encounter_id FROM encounter WHERE visit_id = :visitId AND patient_id = :patientId)")
    List<ObsEntity> getPatientObsByVisitId(long patientId, long visitId);

    @Query("SELECT * FROM obs WHERE encounter_id IN (SELECT encounter_id FROM encounter WHERE visit_id = :visitId AND patient_id = :patientId) AND concept_id = :conceptId")
    List<ObsEntity> getPatientObsByConceptIdVisitId(long patientId, long conceptId, long visitId);

    //get getPersons by id
    //refactored Vitals DAO into Generic DAO
    @Query("SELECT * FROM obs WHERE encounter_id = :encounterId AND person_id = :patientId")
    List<ObsEntity> getPatientObsByEncounterId(long patientId, long encounterId);

    // Refactored Vitals Data Access Object
    //@Query("SELECT * FROM obs  WHERE  person_id = :patientId  AND concept_id = (select concept_id from openmrs.concept where uuid= :conceptUuid ) AND obs_datetime  ")
    @Query("SELECT * FROM obs LAST_INSERT_ID WHERE concept_id = (select concept_id from concept where uuid= :conceptUuid ) AND person_id = :patientId AND obs_datetime = (SELECT MAX(obs_datetime) FROM obs WHERE  concept_id = (select concept_id from concept where uuid= :conceptUuid )) AND person_id = :patientId")
    LiveData<ObsEntity>getPatientObsValueByConceptId(long patientId, String conceptUuid);
}