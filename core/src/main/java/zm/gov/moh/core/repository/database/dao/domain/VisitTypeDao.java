package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.VisitType;

@Dao
public interface VisitTypeDao {

    //gets all locations
    @Query("SELECT * FROM visit WHERE visit_type_id = :id")
    LiveData<VisitType> getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VisitType... visitTypes);
}