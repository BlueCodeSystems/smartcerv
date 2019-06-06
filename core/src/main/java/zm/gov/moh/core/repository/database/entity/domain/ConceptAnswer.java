package zm.gov.moh.core.repository.database.entity.domain;

import org.threeten.bp.LocalDateTime;

import androidx.room.*;

import com.squareup.moshi.Json;

@Entity(tableName = "concept_answer")
public class ConceptAnswer {

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

    @ColumnInfo(name = "answer_drug")
    @Json(name = "answer_drug")
    private Long answerDrug;

    @ColumnInfo(name = "creator")
    @Json(name = "creator")
    private Long creator;

    @ColumnInfo(name = "date_created")
    @Json(name = "date_created")
    private LocalDateTime dateCreated;

    @ColumnInfo(name = "sort_weight")
    @Json(name = "sort_weight")
    private Double sortWeight;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

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

    public Long getAnswerDrug() {
        return answerDrug;
    }

    public void setAnswerDrug(Long answerDrug) {
        this.answerDrug = answerDrug;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Double getSortWeight() {
        return sortWeight;
    }

    public void setSortWeight(Double sortWeight) {
        this.sortWeight = sortWeight;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}