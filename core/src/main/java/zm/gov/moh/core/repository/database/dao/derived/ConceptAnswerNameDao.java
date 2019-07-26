package zm.gov.moh.core.repository.database.dao.derived;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import zm.gov.moh.core.repository.database.entity.derived.ConceptAnswerName;

@Dao
public interface ConceptAnswerNameDao {

    //get by concept id
    @Query("SELECT concept_answer_id,concept_answer.concept_id,answer_concept,name,sort_weight FROM concept_answer JOIN concept_name ON concept_name.concept_id = concept_answer.answer_concept WHERE concept_answer.concept_id = :id AND concept_name.locale_preferred = 1 ORDER BY sort_weight ASC")
    LiveData<List<ConceptAnswerName>> getByConceptId(long id);
}
