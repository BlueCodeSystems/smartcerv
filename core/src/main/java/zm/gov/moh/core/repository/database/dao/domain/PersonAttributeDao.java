package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;

import org.threeten.bp.LocalDateTime;

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
    PersonAttributeEntity findByPersonEntityId(long id);

    @Query("SELECT * FROM person_attribute WHERE person_id = :id")
    LiveData<PersonAttributeEntity> findByPersonIdObservable(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonAttributeEntity... personAttribute);  // used to insert an array of attributes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonAttributeEntity personAttribute);     // used to insert a single attribute
    //get persons attribute by getPersons id

    @Query("SELECT value, person_attribute_type.uuid AS attributeType FROM person_attribute JOIN person_attribute_type WHERE person_id = :id AND person_attribute_type.date_changed >= :lastModifiedDate ")
    List<PersonAttribute> findByPersonId(long id, LocalDateTime lastModifiedDate);

}
