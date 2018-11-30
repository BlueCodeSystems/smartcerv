package zm.gov.moh.core.repository.database.dao.domain;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.repository.database.entity.domain.LocationAttribute;
import zm.gov.moh.core.repository.database.entity.domain.LocationAttributeType;

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