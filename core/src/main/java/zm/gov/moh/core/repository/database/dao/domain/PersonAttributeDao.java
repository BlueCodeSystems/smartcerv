package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.model.PersonAttribute;
import zm.gov.moh.core.repository.database.entity.domain.PersonAttributeEntity;

@Dao
public interface PersonAttributeDao {

    //gets all person_attributes
    @Query("SELECT * FROM person_attribute")
    List<PersonAttributeEntity> getAll();

    @Query("SELECT * FROM person_attribute WHERE person_id = :id")
    PersonAttributeEntity findByPersonId(long id);

    @Query("SELECT * FROM person_attribute WHERE person_id = :id")
    LiveData<PersonAttributeEntity> findByPersonIdObservable(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonAttributeEntity... personAttribute);  // used to insert an array of attributes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonAttributeEntity personAttribute);     // used to insert a single attribute
}
