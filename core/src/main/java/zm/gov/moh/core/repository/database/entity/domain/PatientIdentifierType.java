package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "patient_identifier_type")
public class PatientIdentifierType {

    @PrimaryKey
    @ColumnInfo(name = "patient_identifier_type_id")
    @Json(name = "patient_identifier_type_id")
    private Long patientIdentifierTypeId;

    @ColumnInfo(name = "name")
    @Json(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    @Json(name = "description")
    private String description;

    @ColumnInfo(name = "format")
    @Json(name = "format")
    private String format;

    @ColumnInfo(name = "check_digit")
    @Json(name = "check_digit")
    private short checkDigit;

    @ColumnInfo(name = "creator")
    @Json(name = "creator")
    private Long creator;

    @ColumnInfo(name = "date_created")
    @Json(name = "date_created")
    private LocalDateTime dateCreated;

    @ColumnInfo(name = "required")
    @Json(name = "required")
    private short required;

    @ColumnInfo(name = "format_description")
    @Json(name = "format_description")
    private String formatDescription;

    @ColumnInfo(name = "validator")
    @Json(name = "validator")
    private String validator;

    @ColumnInfo(name = "location_behavior")
    @Json(name = "location_behavior")
    private String locationBehavior;

    @ColumnInfo(name = "retired")
    @Json(name = "retired")
    private short retired;

    @ColumnInfo(name = "retired_by")
    @Json(name = "retired_by")
    private Long retiredBy;

    @ColumnInfo(name = "date_retired")
    @Json(name = "date_retired")
    private LocalDateTime dateRetired;

    @ColumnInfo(name = "retired_reason")
    @Json(name = "retired_reason")
    private String retiredReason;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    @ColumnInfo(name = "uniqueness_behavior")
    @Json(name = "uniqueness_behavior")
    private String uniquenessBehavior;

    @ColumnInfo(name = "date_changed")
    @Json(name = "date_changed")
    private LocalDateTime dateChanged;

    @ColumnInfo(name = "changed_by")
    @Json(name = "changed_by")
    private Long changedBy;

    public Long getPatientIdentifierTypeId() {
        return patientIdentifierTypeId;
    }

    public void setPatientIdentifierTypeId(Long patientIdentifierTypeId) {
        this.patientIdentifierTypeId = patientIdentifierTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public short getCheckDigit() {
        return checkDigit;
    }

    public void setCheckDigit(short checkDigit) {
        this.checkDigit = checkDigit;
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

    public short getRequired() {
        return required;
    }

    public void setRequired(short required) {
        this.required = required;
    }

    public String getFormatDescription() {
        return formatDescription;
    }

    public void setFormatDescription(String formatDescription) {
        this.formatDescription = formatDescription;
    }

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    public String getLocationBehavior() {
        return locationBehavior;
    }

    public void setLocationBehavior(String locationBehavior) {
        this.locationBehavior = locationBehavior;
    }

    public short getRetired() {
        return retired;
    }

    public void setRetired(short retired) {
        this.retired = retired;
    }

    public Long getRetiredBy() {
        return retiredBy;
    }

    public void setRetiredBy(Long retiredBy) {
        this.retiredBy = retiredBy;
    }

    public LocalDateTime getDateRetired() {
        return dateRetired;
    }

    public void setDateRetired(LocalDateTime dateRetired) {
        this.dateRetired = dateRetired;
    }

    public String getRetiredReason() {
        return retiredReason;
    }

    public void setRetiredReason(String retiredReason) {
        this.retiredReason = retiredReason;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUniquenessBehavior() {
        return uniquenessBehavior;
    }

    public void setUniquenessBehavior(String uniquenessBehavior) {
        this.uniquenessBehavior = uniquenessBehavior;
    }

    public LocalDateTime getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(LocalDateTime dateChanged) {
        this.dateChanged = dateChanged;
    }

    public Long getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Long changedBy) {
        this.changedBy = changedBy;
    }
}
