package zm.gov.moh.core.repository.database.dao.domain;


import androidx.lifecycle.LiveData;

import java.util.List;

import androidx.room.*;
import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;

@Dao
public interface PersonDao extends Synchronizable<Person> {

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

    @Override
    @Query("SELECT * FROM person WHERE person_id NOT IN (:id)")
    Person[] findEntityNotWithId(long[] id);
}