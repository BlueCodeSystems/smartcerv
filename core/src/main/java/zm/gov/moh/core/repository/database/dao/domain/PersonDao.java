package zm.gov.moh.core.repository.database.dao.domain;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.Person;

@Dao
public interface PersonDao {

    //gets all persons
    @Query("SELECT * FROM person")
    LiveData<List<Person>> getAll();

    // Inserts single getPersons
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Person person);

    // Inserts single getPersons
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Person... persons);

    //get getPersons by id
    @Query("SELECT * FROM person WHERE person_id = :id")
    Person findById(long id);

    @Query("SELECT MAX(person_id) FROM person")
    Long getMaxId();
}