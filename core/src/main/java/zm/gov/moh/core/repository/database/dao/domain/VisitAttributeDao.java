package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.VisitAttribute;

@Dao
public interface VisitAttributeDao {

    //gets all locations
    @Query("SELECT * FROM visit_attribute WHERE visit_id = :id")
    LiveData<VisitAttribute> getByVisitId(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VisitAttribute... visitAttributes);
}