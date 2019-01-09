package zm.gov.moh.core.repository.database.entity.derived;

import org.threeten.bp.ZonedDateTime;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ConceptAnswerName {

    @PrimaryKey
    public long concept_answer_id;
    public long concept_id;
    public long answer_concept;
    public String name;
    public Double sort_weight;
}