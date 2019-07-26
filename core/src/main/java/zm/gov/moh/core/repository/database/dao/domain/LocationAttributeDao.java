package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.LocationAttribute;

@Dao
public interface LocationAttributeDao {

    //gets all locations
    @Query("SELECT * FROM location_attribute")
    LiveData<List<LocationAttribute>> getAll();

    //gets all locations
    @Query("SELECT * FROM location_attribute WHERE location_attribute_id = :id")
    LiveData<LocationAttribute> getById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LocationAttribute... locationAttributes);

    @Query("SELECT * FROM location_attribute WHERE location_id = :id")
    LiveData<LocationAttribute> getByLocationId(Long id);
}