package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.LocationTag;

@Dao
public interface LocationTagDao {

    // Inserts single
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LocationTag locationTag);

    // Inserts single
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LocationTag... locationTag);

    //get getPersons by id
    @Query("SELECT * FROM location_tag WHERE location_tag_id = :id")
    LocationTag findById(Long id);
}
