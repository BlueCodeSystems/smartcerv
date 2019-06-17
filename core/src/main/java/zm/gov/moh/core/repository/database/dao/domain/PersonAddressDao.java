package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;

@Dao
public interface PersonAddressDao extends Synchronizable<PersonAddress> {

    //gets all persons
    @Query("SELECT * FROM person_address WHERE preferred = 1")
    List<PersonAddress> getAll();

    // Inserts single getPersons
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonAddress personAddress);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonAddress... personAddresses);

    //get persons address by getPersons id
    @Query("SELECT * FROM person_address WHERE person_id = :id AND preferred = 1")
    LiveData<PersonAddress> findByPersonIdObservable(long id);


    @Query("SELECT * FROM person_address WHERE person_id = :id AND preferred = 1")
    PersonAddress findByPersonId(long id);

    @Query("SELECT MAX(person_address_id) FROM person_address")
    Long getMaxId();

    @Override
    @Query("SELECT * FROM (SELECT * FROM person_address WHERE person_address_id NOT IN (:id)) WHERE person_address_id >= :offsetId")
    PersonAddress[] findEntityNotWithId(long offsetId,long... id);
}
