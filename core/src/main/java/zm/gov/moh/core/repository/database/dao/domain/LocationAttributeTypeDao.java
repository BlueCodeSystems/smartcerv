package zm.gov.moh.core.repository.database.dao.domain;


import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.LocationAttributeType;

@Dao
public interface LocationAttributeTypeDao {

    //gets all locations
    @Query("SELECT * FROM location_attribute_type")
    LiveData<List<LocationAttributeType>> getAll();

    //gets all locations
    @Query("SELECT * FROM location_attribute_type WHERE location_attribute_type_id = :id")
    LiveData<LocationAttributeType> getById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LocationAttributeType... locationAttributeTypes);
}