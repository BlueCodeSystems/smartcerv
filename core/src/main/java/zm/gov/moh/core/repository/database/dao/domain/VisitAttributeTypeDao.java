package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.VisitAttributeType;

@Dao
public interface VisitAttributeTypeDao {

    //gets all locations
    @Query("SELECT * FROM visit_attribute_type WHERE visit_attribute_type_id = :id")
    LiveData<VisitAttributeType> getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VisitAttributeType... visitAttributeTypes);
}