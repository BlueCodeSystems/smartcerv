package zm.gov.moh.core.repository.database.dao.domain;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import zm.gov.moh.core.repository.database.entity.domain.Concept;
import zm.gov.moh.core.repository.database.entity.domain.ConceptAnswer;

@Dao
public interface ConceptDao {

    //get by concept id
    @Query("SELECT * FROM concept")
    List<Concept> getAll();

    //get by concept id
    @Query("SELECT concept_id FROM concept WHERE uuid = :uuid")
    Long getConceptIdByUuid(String uuid);

    @Query("SELECT concept_id FROM concept WHERE uuid = :uuid")
    LiveData<Long> getConceptIdByUuidAsync(String uuid);

    @Query("SELECT concept_id FROM concept WHERE uuid IN (:uuid)")
    LiveData<List<Long>> getConceptIdByUuid(List<String> uuid);

    @Query("SELECT uuid FROM concept WHERE concept_id = :conceptId")
    String getConceptUuidById(long conceptId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Concept... concepts);
}
