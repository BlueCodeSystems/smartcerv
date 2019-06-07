package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalDateTime;

@Entity
public class Visit {

    @PrimaryKey
    @ColumnInfo(name = "visit_id")
    @Json(name = "visit_id")
    private long visitId;

    @ColumnInfo(name = "patient_id")
    @Json(name = "patient_id")
    private long patientId;

    @ColumnInfo(name = "visit_type_id")
    @Json(name = "visit_type_id")
    private long visitTypeId;

    @ColumnInfo(name = "date_started")
    @Json(name = "date_started")
    private LocalDateTime dateStarted;

    @ColumnInfo(name = "date_stopped")
    @Json(name = "date_stopped")
    private LocalDateTime dateStopped;

    @ColumnInfo(name = "indication_concept_id")
    @Json(name = "indication_concept_id")
    private Long indicationConceptId;

    @ColumnInfo(name = "location_id")
    @Json(name = "location_id")
    private Long locationId;

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
    private short voided;

    @ColumnInfo(name = "voided_by")
    @Json(name = "voided_by")
    private Long voidedBy;

    @ColumnInfo(name = "date_voided")
    @Json(name = "date_voided")
    private LocalDateTime dateVoided;

    @ColumnInfo(name = "void_reason")
    @Json(name = "void_reason")
    private String voidReason;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    public Visit(){

    }

    @Ignore
    public Visit(long visitId, long visitTypeId, long patientId, Long locationId, Long creator,LocalDateTime dateStarted){

        this.visitId = visitId;
        this.visitTypeId = visitTypeId;
        this.patientId = patientId;
        this.locationId = locationId;
        this.creator = creator;
        this.dateStarted = dateStarted;
        this.dateCreated = dateStarted;
    }

    @Ignore
    public Visit(long visitId, long visitTypeId, long patientId, Long locationId, Long creator,LocalDateTime dateStarted,LocalDateTime dateStopped){

        this.visitId = visitId;
        this.visitTypeId = visitTypeId;
        this.patientId = patientId;
        this.locationId = locationId;
        this.creator = creator;
        this.dateStarted = dateStarted;
        this.dateCreated = dateStarted;
        this.dateStopped = dateStopped;
    }

    //Setters and Getters
    public long getVisitId() {
        return visitId;
    }

    public void setVisitId(long visitId) {
        this.visitId = visitId;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getVisitTypeId() {
        return visitTypeId;
    }

    public void setVisitTypeId(long visitTypeId) {
        this.visitTypeId = visitTypeId;
    }

    public LocalDateTime getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(LocalDateTime dateStarted) {
        this.dateStarted = dateStarted;
    }

    public LocalDateTime getDateStopped() {
        return dateStopped;
    }

    public void setDateStopped(LocalDateTime dateStopped) {
        this.dateStopped = dateStopped;
    }

    public Long getIndicationConceptId() {
        return indicationConceptId;
    }

    public void setIndicationConceptId(Long indicationConceptId) {
        this.indicationConceptId = indicationConceptId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
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

    public short getVoided() {
        return voided;
    }

    public void setVoided(short voided) {
        this.voided = voided;
    }

    public Long getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Long voidedBy) {
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