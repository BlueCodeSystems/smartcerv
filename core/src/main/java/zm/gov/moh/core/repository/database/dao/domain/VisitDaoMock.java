package zm.gov.moh.core.repository.database.dao.domain;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntityMock;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;

@Dao
public interface VisitDaoMock extends Synchronizable<Long> {

    @Query("SELECT MAX(visit_id) FROM Visit")
    Long getMaxId();

    @Query("SELECT visit_id FROM Visit")
    List<Long> getIds();

    //gets all locations
    @Query("SELECT * FROM Visit WHERE patient_id = :id")
    LiveData<List<VisitEntity>> getByPatientIdLive(long id);

    @Query("SELECT * FROM Visit WHERE patient_id = :id")
    VisitEntity[] getByPatientId(long id);

    @Query("SELECT * FROM Visit WHERE visit_id IN (:id) AND voided=0")
    List<VisitEntity> getById(Long[] id);

    @Query("SELECT * FROM Visit WHERE visit_id = :id")
    VisitEntity getById(Long id);

    @Query("SELECT * FROM Visit WHERE visit_type_id IN (:visitTypes) AND patient_id = :id AND voided != 1")
    LiveData<List<VisitEntity>> getByPatientIdVisitTypeId(long id, long... visitTypes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VisitEntity... visits);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VisitEntityMock[] entities);

    @Update
    void updateVisit(VisitEntity visit);

    @Update
    void updateVisit(EntityMetadata entityMetadata);

    @Override
    @Query("SELECT visit_id FROM (SELECT * FROM Visit WHERE visit_id NOT IN (:id)) WHERE visit_id >= :offsetId")
    Long[] findEntityNotWithId(long offsetId, long... id);




}

