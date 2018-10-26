package zm.gov.moh.core.repository.database.dao.domain;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.PersonAttribute;

@Dao
public interface PersonAttributeDao {

    //gets all person_attributes
    @Query("SELECT * FROM person_attribute")
    List<PersonAttribute> getAll();

    // Inserts single person_attribute
    @Insert
    void insert(PersonAttribute personAttribute);

    //get persons attribute by person id
    @Query("SELECT * FROM person_attribute WHERE person_id = :id")
    PersonAttribute findByPersonId(long id);
}
