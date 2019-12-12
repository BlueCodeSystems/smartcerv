package zm.gov.moh.core.repository.database.dao.derived;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.derived.Client;

@Dao
public interface ClientDao {

    @Query("SELECT patient.patient_id AS patient_id ,identifier, given_name, family_name, gender, birthdate FROM person  JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 3 AND patient_identifier.voided=0 ORDER BY patient.date_created DESC ")
    LiveData<List<Client>> findAllClients();

    @Query("SELECT patient.patient_id AS patient_id ,identifier, given_name, family_name, gender, birthdate FROM person  JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 3 AND patient_identifier.location_id = :locationId  ORDER BY patient.date_created DESC ")
    LiveData<List<Client>> findClientsByLocation(long locationId);

    @Query("SELECT patient.patient_id AS patient_id ,identifier, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 3 ")
    List<Client> getAllClients();

    //get client by id
    @Query("SELECT patient.patient_id AS patient_id,identifier, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 3 AND patient.patient_id = :id")
    LiveData<Client> findById(long id);

    @Query("SELECT patient.patient_id AS patient_id,identifier, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 3 AND patient.patient_id IN (:id)")
    LiveData<List<Client>> findById(List<Long> id);

   //update client_id

    @Query("Update patient_identifier SET identifier=:identifier WHERE patient_id=:patientId AND identifier_type=4")
   void updateClientIdByPatientId(Long patientId,String identifier);


    //update clients phone number
    @Query("Update person_attribute SET value=:phone WHERE person_id=:patientId AND person_attribute_id=8")
    void updatePhoneNumberByPatientId(Long patientId,String phone);

    //update clients given name and family name

    @Query("Update person_name SET given_name=:givenName, family_name=:familyName WHERE person_id=:patientId")
    void updateNamesById(Long patientId,String givenName,String familyName);

    //update address
    @Query("Update person_address SET address1=:address WHERE person_id=:patientId")
    void updateAddressById(Long patientId,String address);

    //update birthdate

    @Query("Update person SET birthdate=:birthdate WHERE person_id=:patientId")
    void updateBirthdateId(Long patientId,String birthdate);

}