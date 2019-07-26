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

    @Query("SELECT uuid FROM visit_type WHERE visit_type_id = :id")
    String getUuidVisitTypeById(long id);

    @Query("SELECT name FROM visit_type WHERE visit_type_id = :visitTypeId")
    String getVisitTypeById(long visitTypeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VisitType... visitTypes);
}