package zm.gov.moh.core.repository.database.dao.derived;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.derived.Client;

@Dao
public interface ClientDao {

    @Query("SELECT person.person_id, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id WHERE person_name.preferred = 1")
    LiveData<List<Client>> findAllClients();

    //get client by id
    @Query("SELECT person.person_id, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id WHERE patient.patient_id = :id AND person_name.preferred = 1")
    LiveData<Client> findById(long id);

    //get client by id
    @Query("SELECT person.person_id, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id WHERE (:term LIKE person.person_id) OR (:term LIKE person_name.given_name) OR (:term LIKE person_name.family_name)")
    LiveData<List<Client>> findByTerm(String term);
}