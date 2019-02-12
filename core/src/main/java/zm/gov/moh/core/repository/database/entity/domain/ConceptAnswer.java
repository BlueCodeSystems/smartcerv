package zm.gov.moh.core.repository.database.entity.domain;

import org.threeten.bp.LocalDateTime;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "concept_answer")
public class ConceptAnswer {

    @PrimaryKey
    public long concept_answer_id;
    public long concept_id;
    public long answer_concept;
    public Long answer_drug;
    public Long creator;
    public LocalDateTime date_created;
    public Double sort_weight;
    public String uuid;
}