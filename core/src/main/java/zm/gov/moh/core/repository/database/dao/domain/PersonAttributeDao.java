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

    //get persons attribute by getPersons id
    //@Query("SELECT value, person_attribute_type.uuid AS attributeType FROM person_attribute JOIN person_attribute_type WHERE person_id = :id")
    //List<PersonAttribute> findByPersonId(long id);

    @Query("SELECT * FROM person_attribute WHERE person_id = :id")
    PersonAttributeEntity findByPersonId(long id);

    @Query("SELECT * FROM person_attribute WHERE person_id = :id")
    LiveData<PersonAttributeEntity> findByPersonIdObservable(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonAttributeEntity... personAttribute);
}
