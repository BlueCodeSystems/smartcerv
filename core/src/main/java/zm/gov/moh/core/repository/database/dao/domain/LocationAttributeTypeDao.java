package zm.gov.moh.core.repository.database.dao.domain;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.Location;
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