package zm.gov.moh.core.repository.database.dao.domain;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.repository.database.entity.domain.LocationTag;
import zm.gov.moh.core.repository.database.entity.domain.Person;

@Dao
public interface LocationTagDao {

    // Inserts single
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LocationTag locationTag);

    // Inserts single
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LocationTag... locationTag);

    //get person by id
    @Query("SELECT * FROM location_tag WHERE location_tag_id = :id")
    LocationTag findById(Long id);
}
