package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;

@Dao
public interface PersonAddressDao {

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
    LiveData<PersonAddress> findByPersonId(long id);

    @Query("SELECT MAX(person_address_id) FROM person_address")
    Long getMaxId();
}
