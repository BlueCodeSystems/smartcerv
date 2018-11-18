package zm.gov.moh.core.repository.database.dao.domain;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

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

    //get person by id
    @Query("SELECT * FROM location_tag_map WHERE location_id = :id")
    LocationTagMap findByLocationId(Long id);
}