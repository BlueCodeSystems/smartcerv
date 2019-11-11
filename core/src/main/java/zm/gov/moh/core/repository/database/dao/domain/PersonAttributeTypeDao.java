package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.PersonAttributeType;

@Dao
public interface PersonAttributeTypeDao {

    //gets all persons attribute type
    @Query("SELECT * FROM person_attribute_type")
    List<PersonAttributeType> getAll();

    @Query("SELECT person_attribute_type_id FROM person_attribute_type WHERE uuid = :uuid")
    Long getPersonAttributeByUuid(String uuid);

    //get persons address by getPersons id
    @Query("SELECT * FROM person_attribute_type WHERE person_attribute_type_id = :id")
    PersonAttributeType findById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonAttributeType... personAttributeTypes);
}