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
    @Query("SELECT concept_id FROM concept WHERE uuid = :uuid")
    Long getConceptIdByUuid(String uuid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Concept... concepts);
}
