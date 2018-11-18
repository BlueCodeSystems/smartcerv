package zm.gov.moh.core.repository.database.dao.domain;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.Location;

@Dao
public interface LocationDao {

    //gets all locations
    @Query("SELECT * FROM location ORDER BY name ASC")
    LiveData<List<Location>> getAll();

    //gets all locations
    @Query("SELECT * FROM location WHERE parent_location = :id")
    LiveData<List<Location>> getChild(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Location... locations);

    //get location by id
    @Query("SELECT * FROM location WHERE location_id = :id")
    LiveData<Location> findById(Long id);
}
