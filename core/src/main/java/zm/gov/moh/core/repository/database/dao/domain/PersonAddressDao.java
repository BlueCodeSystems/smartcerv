package zm.gov.moh.core.repository.database.dao.domain;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;

@Dao
public interface PersonAddressDao {

    //gets all persons
    @Query("SELECT * FROM person_address")
    List<PersonAddress> getAll();

    // Inserts single person
    @Insert
    void insert(PersonAddress personAddress);

    //get persons address by person id
    @Query("SELECT * FROM person_address WHERE person_id = :id")
    PersonAddress findByPersonId(long id);
}
