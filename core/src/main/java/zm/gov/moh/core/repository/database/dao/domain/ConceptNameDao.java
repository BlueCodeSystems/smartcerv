package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import zm.gov.moh.core.repository.database.entity.domain.ConceptName;
import zm.gov.moh.core.repository.database.entity.domain.Encounter;

@Dao
public interface ConceptNameDao {

    @Query("SELECT * FROM concept_name WHERE concept_id = :id")
    LiveData<ConceptName> getConceptNameByConceptId(long id);

    @Query("SELECT * FROM concept_name WHERE concept_id IN (:id)")
    LiveData<ConceptName> getConceptNameByConceptId(long ...id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ConceptName... conceptAnswers);
}
