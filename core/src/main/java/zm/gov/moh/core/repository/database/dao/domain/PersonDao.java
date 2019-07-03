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

    @Query("SELECT uuid FROM person WHERE person_id = :patientId")
    String findByPatientId(long patientId);

    @Query("UPDATE person SET person_id = :remote WHERE person_id = :local ")
    void replacePerson(long local, long remote );

    @Query("SELECT MAX(person_id) FROM person")
    Long getMaxId();

    @Override
    @Query("SELECT * FROM (SELECT * FROM person WHERE person_id NOT IN (:id)) WHERE person_id >= :offsetId")
    Person[] findEntityNotWithId(long offsetId,long[] id);


}