package zm.gov.moh.core.repository.database.dao.domain;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import zm.gov.moh.core.repository.database.entity.domain.ConceptAnswer;

@Dao
public interface ConceptAnswerDao {

    //get by concept id
    @Query("SELECT * FROM concept_answer WHERE concept_id = :id")
    List<ConceptAnswer>getConceptAnswerIdByConceptId(long id);

    @Query("SELECT * FROM concept_answer")
    List<ConceptAnswer>getAll();

    @Query("DELETE FROM concept_answer")
    void removeAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ConceptAnswer... conceptAnswers);
}
