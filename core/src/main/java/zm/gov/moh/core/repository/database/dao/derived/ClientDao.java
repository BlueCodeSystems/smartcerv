package zm.gov.moh.core.repository.database.dao.derived;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.derived.Client;

@Dao
public interface ClientDao {

    @Query("SELECT patient.patient_id AS patient_id ,identifier, given_name, family_name, gender, birthdate FROM person  JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 3 ORDER BY patient.date_created DESC ")
    LiveData<List<Client>> findAllClients();

    @Query("SELECT patient.patient_id AS patient_id ,identifier, given_name, family_name, gender, birthdate FROM person  JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type =3 AND patient_identifier.location_id = :locationId  ORDER BY patient.date_created DESC ")
    LiveData<List<Client>> findClientsByLocation(long locationId);

    @Query("SELECT patient.patient_id AS patient_id ,identifier, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 3 ")
    List<Client> getAllClients();

    //get client by id
    @Query("SELECT patient.patient_id AS patient_id,identifier, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 3 AND patient.patient_id = :id")
    LiveData<Client> findById(long id);

    @Query("SELECT patient.patient_id AS patient_id,identifier, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 3 AND patient.patient_id IN (:id)")
    LiveData<List<Client>> findById(List<Long> id);

    //get all clients that are not enrolled yet
   // @Query("SELECT patient.patient_id AS patient_id ,identifier, given_name, family_name, gender, birthdate FROM person  JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 3 AND patient_identifier.location_id = :locationId  ORDER BY patient.date_created DESC ")

    @Query("SELECT patient.patient_id AS patient_id ,identifier, given_name, family_name, gender, birthdate FROM person  JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 3 AND patient_identifier.location_id = :locationId AND patient_identifier.patient_id NOT IN(SELECT person.person_id From person JOIN patient_identifier ON person.person_id= patient_identifier.patient_id WHERE patient_identifier.identifier_type=4) ORDER BY patient.date_created DESC")
    LiveData<List<Client>> getAllClientsNotEnrolledByLocation(long locationId);
}