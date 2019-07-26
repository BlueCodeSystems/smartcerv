package zm.gov.moh.core.repository.database.entity.derived;


import androidx.room.*;

import com.squareup.moshi.Json;

@Entity
public class ConceptAnswerName {

    @PrimaryKey
    @ColumnInfo(name = "concept_answer_id")
    @Json(name = "concept_answer_id")
    private long conceptAnswerId;

    @ColumnInfo(name = "concept_id")
    @Json(name = "concept_id")
    private long conceptId;

    @ColumnInfo(name = "answer_concept")
    @Json(name = "answer_concept")
    private long answerConcept;

    @ColumnInfo(name = "name")
    @Json(name = "name")
    private String name;

    @ColumnInfo(name = "sort_weight")
    @Json(name = "sort_weight")
    private Double sortWeight;

    public long getConceptAnswerId() {
        return conceptAnswerId;
    }

    public void setConceptAnswerId(long conceptAnswerId) {
        this.conceptAnswerId = conceptAnswerId;
    }

    public long getConceptId() {
        return conceptId;
    }

    public void setConceptId(long conceptId) {
        this.conceptId = conceptId;
    }

    public long getAnswerConcept() {
        return answerConcept;
    }

    public void setAnswerConcept(long answerConcept) {
        this.answerConcept = answerConcept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSortWeight() {
        return sortWeight;
    }

    public void setSortWeight(Double sortWeight) {
        this.sortWeight = sortWeight;
    }
}