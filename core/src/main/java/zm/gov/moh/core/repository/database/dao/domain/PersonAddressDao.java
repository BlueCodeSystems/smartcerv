package zm.gov.moh.core.repository.database.dao.domain;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;

@Dao
public interface PersonAddressDao {

    //gets all persons
    @Query("SELECT * FROM person_address WHERE preferred = 1")
    List<PersonAddress> getAll();

    // Inserts single person
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonAddress personAddress);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonAddress... personAddresses);

    //get persons address by person id
    @Query("SELECT * FROM person_address WHERE person_id = :id AND preferred = 1")
    LiveData<PersonAddress> findByPersonId(long id);
}
