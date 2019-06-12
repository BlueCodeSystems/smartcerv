package zm.gov.moh.core.repository.database.dao.domain;

import org.threeten.bp.ZonedDateTime;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.Obs;
import zm.gov.moh.core.repository.database.entity.domain.Visit;

@Dao
public interface VisitDao extends Synchronizable<Visit> {

    @Query("SELECT MAX(visit_id) FROM visit")
    Long getMaxId();
    //gets all locations
    @Query("SELECT * FROM visit WHERE patient_id = :id")
    LiveData<List<Visit>> getByPatientId(long id);

    @Query("SELECT * FROM visit WHERE visit_type_id IN (:visitTypes) AND patient_id = :id")
    LiveData<List<Visit>> getByPatientIdVisitTypeId(long id, long... visitTypes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Visit... visits);

    @Update
    void updateVisit(Visit visit);

    @Override
    @Query("SELECT * FROM (SELECT * FROM visit WHERE visit_id NOT IN (:id)) WHERE visit_id >= :offsetId")
    Visit[] findEntityNotWithId(long offsetId,long... id);
}