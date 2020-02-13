package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "allergy")
public class Allergy {

    @PrimaryKey
    @ColumnInfo(name = "allergy_id")
    @Json(name = "allergy_id")
    private long allergyId;

    @ColumnInfo(name = "patient_id")
    @Json(name = "patient_id")
    private long patientId;

    @ColumnInfo(name = "severity_concept_id")
    @Json(name = "severity_concept_id")
    private long severityConceptId;

    @ColumnInfo(name = "coded_allergen")
    @Json(name = "coded_allergen")
    private long codedAllergen;

    @ColumnInfo(name = "non_coded_allergen")
    @Json(name = "non_coded_allergen")
    private String nonCodedAllergen;

    @ColumnInfo(name = "allergen_type")
    @Json(name = "allergen_type")
    private String allergenType;

    @ColumnInfo(name = "comment")
    @Json(name = "comment")
    private String comment;

    @ColumnInfo(name = "creator")
    @Json(name = "creator")
    private Long creator;

    @ColumnInfo(name = "date_created")
    @Json(name = "date_created")
    private LocalDateTime dateCreated;

    @ColumnInfo(name = "changed_by")
    @Json(name = "changed_by")
    private Long changedBy;

    @ColumnInfo(name = "date_changed")
    @Json(name = "date_changed")
    private LocalDateTime dateChanged;

    @ColumnInfo(name = "voided")
    @Json(name = "voided")
    private Integer voided;

    @ColumnInfo(name = "voided_by")
    @Json(name = "voided_by")
    private Integer voidedBy;

    @ColumnInfo(name = "date_voided")
    @Json(name = "date_voided")
    private LocalDateTime dateVoided;

    @ColumnInfo(name = "void_reason")
    @Json(name = "void_reason")
    private String voidReason;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    public long getAllergyId() {
        return allergyId;
    }

    public void setAllergyId(long allergyId) {
        this.allergyId = allergyId;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getSeverityConceptId() {
        return severityConceptId;
    }

    public void setSeverityConceptId(long severityConceptId) {
        this.severityConceptId = severityConceptId;
    }

    public long getCodedAllergen() {
        return codedAllergen;
    }

    public void setCodedAllergen(long codedAllergen) {
        this.codedAllergen = codedAllergen;
    }

    public String getNonCodedAllergen() {
        return nonCodedAllergen;
    }

    public void setNonCodedAllergen(String nonCodedAllergen) {
        this.nonCodedAllergen = nonCodedAllergen;
    }

    public String getAllergenType() {
        return allergenType;
    }

    public void setAllergenType(String allergenType) {
        this.allergenType = allergenType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Long getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Long changedBy) {
        this.changedBy = changedBy;
    }

    public LocalDateTime getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(LocalDateTime dateChanged) {
        this.dateChanged = dateChanged;
    }

    public Integer getVoided() {
        return voided;
    }

    public void setVoided(Integer voided) {
        this.voided = voided;
    }

    public Integer getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Integer voidedBy) {
        this.voidedBy = voidedBy;
    }

    public LocalDateTime getDateVoided() {
        return dateVoided;
    }

    public void setDateVoided(LocalDateTime dateVoided) {
        this.dateVoided = dateVoided;
    }

    public String getVoidReason() {
        return voidReason;
    }

    public void setVoidReason(String voidReason) {
        this.voidReason = voidReason;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
