package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;
import zm.gov.moh.core.repository.database.entity.SynchronizableEntity;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "encounter")
public class Encounter extends SynchronizableEntity {

    @PrimaryKey
    @ColumnInfo(name = "encounter_id")
    @Json(name = "encounter_id")
    private long encounterId;

    @ColumnInfo(name = "encounter_type")
    @Json(name = "encounter_type")
    private long encounterType;

    @ColumnInfo(name = "patient_id")
    @Json(name = "patient_id")
    private long patientId;

    @ColumnInfo(name = "location_id")
    @Json(name = "location_id")
    private long locationId;

    @ColumnInfo(name = "form_id")
    @Json(name = "form_id")
    private long formId;

    @ColumnInfo(name = "encounter_datetime")
    @Json(name = "encounter_datetime")
    private LocalDateTime encounterDatetime;

    @ColumnInfo(name = "creator")
    @Json(name = "creator")
    private Long creator;

    @ColumnInfo(name = "date_created")
    @Json(name = "date_created")
    private LocalDateTime dateCreated;

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

    @ColumnInfo(name = "changed_by")
    @Json(name = "changed_by")
    private Long changedBy;

    @ColumnInfo(name = "date_changed")
    @Json(name = "date_changed")
    private LocalDateTime dateChanged;

    @ColumnInfo(name = "visit_id")
    @Json(name = "visit_id")
    private long visitId;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    public Encounter(){

    }

    @Ignore
    public Encounter(long encounterId,long encounterType, long patientId, long locationId,long visitId, long creator, LocalDateTime zonedDatetimeNow){

        this.encounterId = encounterId;
        this.encounterType = encounterType;
        this.patientId = patientId;
        this.locationId = locationId;
        this.visitId = visitId;
        this.creator = creator;
        this.encounterDatetime = zonedDatetimeNow;
        this.dateCreated = zonedDatetimeNow;
    }

    public long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(long encounterId) {
        this.encounterId = encounterId;
    }

    public long getEncounterType() {
        return encounterType;
    }

    public void setEncounterType(long encounterType) {
        this.encounterType = encounterType;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public long getFormId() {
        return formId;
    }

    public void setFormId(long formId) {
        this.formId = formId;
    }

    public LocalDateTime getEncounterDatetime() {
        return encounterDatetime;
    }

    public void setEncounterDatetime(LocalDateTime encounterDatetime) {
        this.encounterDatetime = encounterDatetime;
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

    public long getVisitId() {
        return visitId;
    }

    public void setVisitId(long visitId) {
        this.visitId = visitId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public long getId() {
        return encounterId;
    }
}
