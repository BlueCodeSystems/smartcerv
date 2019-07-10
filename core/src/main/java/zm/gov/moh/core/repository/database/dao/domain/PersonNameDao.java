package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;

@Dao
public interface PersonNameDao extends Synchronizable<PersonName> {

    @Query("SELECT person_name_id FROM person_name")
    List<Long> getIds();

    //gets all persons attribute type
    @Query("SELECT * FROM person_name")
    List<PersonName> getAll();

    // Inserts single getPersons name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonName personName);

    // Inserts single getPersons name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonName... personNames);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<PersonName> personNames);

    //get persons name by getPersons id
    @Query("SELECT * FROM person_name WHERE person_id = :id")
    PersonName findPersonNameById(long id);

    @Query("UPDATE person_name SET person_id = :remote WHERE person_id = :local")
    void replacePerson(long local, long remote);

    //get persons name by getPersons id
    @Override
    @Query("SELECT * FROM (SELECT * FROM person_name WHERE person_name_id NOT IN (:id)) WHERE person_name_id >= :offsetId")
    PersonName[] findEntityNotWithId(long offsetId,long... id);

    @Query("SELECT MAX(person_name_id) FROM person_name")
    Long getMaxId();
}