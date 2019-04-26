package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.Location;

@Dao
public interface LocationDao {

    //gets all locations
    @Query("SELECT * FROM location ORDER BY name ASC")
    LiveData<List<Location>> getAll();

    @Query("SELECT location.* FROM location JOIN location_tag_map ON location.location_id = location_tag_map.location_id WHERE location_tag_id = :id ORDER BY name ASC")
    LiveData<List<Location>> getByTagId(long id);

    @Query("SELECT location.* FROM location JOIN location_tag_map ON location.location_id = location_tag_map.location_id WHERE location_tag_id = (SELECT location_tag_id FROM location_tag WHERE uuid = :uuid) ORDER BY name ASC")
    LiveData<List<Location>> getByTagUuid(String uuid);

    //gets all locations
    @Query("SELECT * FROM location WHERE location_id = (SELECT parent_location FROM location WHERE location_id = :id)")
    LiveData<Location> getParentByChildId(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Location... locations);

    //get getLocations by id
    @Query("SELECT * FROM location WHERE location_id = :id")
    LiveData<Location> findById(Long id);

    //get getLocations by id
    @Query("SELECT location.* FROM location JOIN patient_identifier ON patient_identifier.location_id = location.location_id WHERE patient_identifier.patient_id = :id AND identifier_type = 3")
    LiveData<Location> getByPatientId(Long id);

    //get getLocations by id
    @Query("SELECT location.* FROM location JOIN patient_identifier ON patient_identifier.location_id = location.location_id WHERE patient_identifier.patient_id = :id AND identifier_type = :patient_identifier_type_id")
    LiveData<Location> getByPatientId(Long id,Long patient_identifier_type_id);
}
