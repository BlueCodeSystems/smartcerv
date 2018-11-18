package zm.gov.moh.core.repository.database.dao.domain;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.PersonName;

@Dao
public interface PersonNameDao {

    //gets all persons attribute type
    @Query("SELECT * FROM person_name")
    List<PersonName> getAll();

    // Inserts single person name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonName personName);

    // Inserts single person name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonName... personNames);

    //get persons name by person id
    @Query("SELECT * FROM person_name WHERE person_id = :id")
    PersonName findPersonNameById(long id);
}