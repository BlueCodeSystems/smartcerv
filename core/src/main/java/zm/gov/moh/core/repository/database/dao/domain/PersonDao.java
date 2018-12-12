package zm.gov.moh.core.repository.database.dao.domain;


import androidx.lifecycle.LiveData;

import java.util.List;

import androidx.room.*;
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