package zm.gov.moh.core.repository.database.dao.domain;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.repository.database.entity.domain.LocationTagMap;

@Dao
public interface LocationDao {

    //gets all locations
    @Query("SELECT * FROM location ORDER BY name ASC")
    LiveData<List<Location>> getAll();

    @Query("SELECT location.* FROM location JOIN location_tag_map ON location.location_id = location_tag_map.location_id WHERE location_tag_id = :id ORDER BY name ASC")
    LiveData<List<Location>> getByTagId(long id);

    //gets all locations
    @Query("SELECT * FROM location WHERE parent_location = :id")
    LiveData<List<Location>> getChild(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Location... locations);

    //get getLocations by id
    @Query("SELECT * FROM location WHERE location_id = :id")
    LiveData<Location> findById(Long id);

    //get getLocations by id
    @Query("SELECT location.* FROM location JOIN patient_identifier ON patient_identifier.location_id = location.location_id WHERE patient_identifier.patient_id = :id AND identifier_type = 3")
    LiveData<Location> getByPatientId(Long id);
}
