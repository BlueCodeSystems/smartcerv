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


    @Query("SELECT * FROM  Visit WHERE visit_id >= :offset")
    VisitEntity getByDate(long offset);

    @Query("DELETE FROM Visit WHERE visit_id IN (:visitId) AND visit_id >= :offset")
    void deleteById(long[] visitId, long offset);

    @Query("SELECT * FROM Visit WHERE visit_id IN (:id) AND voided=0")
    List<VisitEntity> getById(Long[] id);

    @Query("SELECT * FROM Visit WHERE visit_id = :id")
    VisitEntity getById(Long id);

    @Query("SELECT * FROM Visit WHERE visit_type_id IN (:visitTypes) AND patient_id = :id AND voided != 1")
    LiveData<List<VisitEntity>> getByPatientIdVisitTypeId(long id, long... visitTypes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VisitEntity... visits);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert1(EntityMetadata... visits);

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    //void insert2(Long[] id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VisitEntityMock[] entities);

    @Update
    void updateVisit(VisitEntity visit);

    @Update
    void updateVisit(EntityMetadata entityMetadata);

    @Override
    @Query("SELECT visit_id FROM (SELECT * FROM Visit WHERE visit_id NOT IN (:id)) WHERE visit_id >= :offsetId")
    Long[] findEntityNotWithId(long offsetId, long... id);

    @Query("SELECT visit_id FROM (SELECT * FROM visit WHERE NOT EXISTS (SELECT DISTINCT entity_id FROM entity_metadata WHERE entity_type_id = :entityTypeId AND remote_status_code = :remoteStatus AND entity_metadata.entity_id = visit.visit_id)) WHERE visit_id >= :offsetId")
    Long[] findEntityNotWithId2(long offsetId, int entityTypeId, short remoteStatus);

    @Query("UPDATE visit SET patient_id = :remotePatientId WHERE visit_id IN (SELECT visit_id FROM visit WHERE patient_id = :localPatientId)")
    void replaceLocalPatientId(long localPatientId, long remotePatientId);

    //void encounters
    @Query("Update encounter SET voided=1 WHERE visit_id=:visitId AND location_id=:locationId")
    void abortVisitEncounter(long visitId, long locationId);

    //void visit
    @Query("Update visit SET voided=1 WHERE visit_id=:visitId AND location_id=:locationId")
    void abortVisit(long visitId, long locationId);

    //void obs by visit id

    @Query("Update obs SET voided=1 WHERE obs.person_id IN(Select obs.person_id from obs join encounter on obs.encounter_id=encounter.encounter_id WHERE encounter.visit_id=:visitId) AND location_id=:locationId")
    void abortVisitObs(long visitId, long locationId);


}

