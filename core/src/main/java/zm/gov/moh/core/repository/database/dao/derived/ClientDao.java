package zm.gov.moh.core.repository.database.dao.derived;



import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.derived.Client;

@Dao
public interface ClientDao {


    @Query("SELECT patient.patient_id AS patient_id ,identifier, given_name, middle_name family_name, gender, birthdate, identifier_type FROM person  JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND person.voided = 0 AND identifier_type = 3 AND patient_identifier.voided=0 ORDER BY patient.date_created DESC ")
    LiveData<List<Client>> findAllClients();

    @Query("SELECT patient.patient_id AS patient_id ,identifier, given_name, middle_name, family_name, gender, birthdate, identifier_type FROM person  JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND person.voided = 0  AND identifier_type =:identifierType AND patient_identifier.location_id = :locationId  ORDER BY patient.date_created DESC ")
    LiveData<List<Client>> findClientsByLocationIdentifierType(long locationId,long identifierType);

    @Query("SELECT patient.patient_id AS patient_id ,identifier, given_name,middle_name, family_name, gender, birthdate, identifier_type FROM person  JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type =3 AND patient_identifier.location_id = :locationId AND patient.patient_id NOT IN (SELECT patient.patient_id AS patient_id FROM person  JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type =:identifierType) ORDER BY patient.date_created DESC ")
    LiveData<List<Client>> findClientsNotEnrollInCervicalCancer(long locationId,long identifierType);

    //get client by id
    @Query("SELECT patient.patient_id AS patient_id,identifier, given_name,middle_name, family_name, gender, birthdate, identifier_type  FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 3 AND patient.patient_id = :id")
    LiveData<Client> findById(long id);

    @Query("SELECT patient.patient_id AS patient_id,identifier, given_name,middle_name, family_name, gender, birthdate, identifier_type  FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 3 AND patient.patient_id IN (:id)")
    LiveData<List<Client>> findById(List<Long> id);

    @Query("SELECT patient.patient_id AS patient_id ,identifier, given_name, family_name,middle_name, gender, birthdate, identifier_type  FROM person  JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 3 AND patient_identifier.location_id = :locationId AND patient_identifier.patient_id NOT IN(SELECT person.person_id From person JOIN patient_identifier ON person.person_id= patient_identifier.patient_id WHERE patient_identifier.identifier_type= :excludeIdentifierIdType) ORDER BY patient.date_created DESC")
    List<Client> getAllClientsNotEnrolledByLocation(long locationId, long excludeIdentifierIdType);

    @Query("SELECT patient.patient_id AS patient_id ,identifier, given_name, family_name,middle_name, gender, birthdate, identifier_type  FROM person  JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE patient.patient_id IN (:searchArgs) AND person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 3 AND patient_identifier.location_id = :locationId AND patient_identifier.patient_id NOT IN(SELECT person.person_id From person JOIN patient_identifier ON person.person_id= patient_identifier.patient_id WHERE patient_identifier.identifier_type= :excludeIdentifierIdType) ORDER BY patient.date_created DESC")
    List<Client> getAllClientsNotEnrolledByLocation(long locationId, long excludeIdentifierIdType, List<Long> searchArgs);

    @Query("SELECT patient.patient_id AS patient_id,identifier, given_name, family_name,middle_name, gender, birthdate, identifier_type FROM person  JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE patient.patient_id IN (:searchArgs) AND person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = :identifierTypeId AND patient_identifier.location_id = :locationId  ORDER BY patient.date_created DESC ")
    List<Client> findClientsByIdentifierTypeLocation(long locationId, long identifierTypeId, List<Long> searchArgs);

    @Query("SELECT patient.patient_id AS patient_id,identifier, given_name, family_name,middle_name, gender, birthdate, identifier_type FROM person  JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = :identifierTypeId AND patient_identifier.location_id = :locationId  ORDER BY patient.date_created DESC ")
    List<Client> findClientsByIdentifierTypeLocation(long locationId, long identifierTypeId);
}