package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.LocationTag;
import zm.gov.moh.core.repository.database.entity.domain.LocationTagMap;
import zm.gov.moh.core.repository.database.entity.domain.Person;

@Dao
public interface LocationTagMapDao {

    // Inserts single
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LocationTagMap locationTagMap);

    // Inserts single
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LocationTagMap... locationTagMaps);

    //get getPersons by id
    @Query("SELECT * FROM location_tag_map WHERE location_id = :id")
    LocationTagMap findByLocationId(Long id);
}