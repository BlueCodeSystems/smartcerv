package zm.gov.moh.core.repository.database.dao.domain;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;

@Dao
public interface VisitDao extends Synchronizable<Long> {

    @Query("SELECT MAX(visit_id) FROM Visit")
    Long getMaxId();
    //gets all locations
    @Query("SELECT * FROM Visit WHERE patient_id = :id")
    LiveData<List<VisitEntity>> getByPatientIdLive(long id);

    @Query("SELECT * FROM Visit WHERE patient_id = :id")
    VisitEntity[] getByPatientId(long id);

    @Query("SELECT * FROM Visit WHERE visit_id IN (:id)")
    List<VisitEntity> getById(Long[] id);

    @Query("SELECT * FROM Visit WHERE visit_type_id IN (:visitTypes) AND patient_id = :id")
    LiveData<List<VisitEntity>> getByPatientIdVisitTypeId(long id, long... visitTypes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VisitEntity... visits);

    @Update
    void updateVisit(VisitEntity visit);

    @Override
    @Query("SELECT visit_id FROM (SELECT * FROM Visit WHERE visit_id NOT IN (:id)) WHERE visit_id >= :offsetId")
    Long[] findEntityNotWithId(long offsetId, long... id);
}