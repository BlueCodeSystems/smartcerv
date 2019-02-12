package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.VisitType;

@Dao
public interface VisitTypeDao {

    //gets all locations
    @Query("SELECT * FROM visit_type WHERE visit_type_id = :id")
    LiveData<VisitType> getById(long id);

    @Query("SELECT visit_type_id FROM visit_type WHERE uuid = :uuid")
    Long getIdByUuid(String uuid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VisitType... visitTypes);
}