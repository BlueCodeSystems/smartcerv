package zm.gov.moh.core.repository.database.dao.domain;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import zm.gov.moh.core.repository.database.entity.domain.ConceptAnswer;
import zm.gov.moh.core.repository.database.entity.domain.Encounter;

@Dao
public interface ConceptAnswerDao {

    //get by concept id
    @Query("SELECT answer_concept FROM concept_answer WHERE concept_id = :id")
    LiveData<List<Long>> getConceptAnswerIdByConceptId(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ConceptAnswer... conceptAnswers);
}
