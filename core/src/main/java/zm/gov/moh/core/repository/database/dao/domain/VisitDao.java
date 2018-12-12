package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.Visit;

@Dao
public interface VisitDao {

    //gets all locations
    @Query("SELECT * FROM visit WHERE patient_id = :id")
    LiveData<Visit> getByPatientId(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Visit... visits);
}