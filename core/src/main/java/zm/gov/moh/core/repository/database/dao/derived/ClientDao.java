package zm.gov.moh.core.repository.database.dao.derived;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import zm.gov.moh.core.repository.database.entity.derived.Client;

@Dao
public interface ClientDao {

    //get client by id
    @Query("SELECT person_id, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id WHERE person_id = :id")
    Client findById(long id);
}