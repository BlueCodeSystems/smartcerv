package zm.gov.moh.core.repository.database.dao.domain;

import org.threeten.bp.ZonedDateTime;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.Visit;

@Dao
public interface VisitDao {

    @Query("SELECT MAX(visit_id) FROM visit")
    Long getMaxId();
    //gets all locations
    @Query("SELECT * FROM visit WHERE patient_id = :id")
    LiveData<Visit> getByPatientId(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Visit... visits);

    @Update
    void updateVisit(Visit visit);
}