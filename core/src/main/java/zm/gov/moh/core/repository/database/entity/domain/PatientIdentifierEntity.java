package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;
import zm.gov.moh.core.repository.database.entity.SynchronizableEntity;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "patient_identifier")
public class PatientIdentifierEntity {

    @PrimaryKey
    @ColumnInfo(name = "patient_identifier_id")
    @Json(name = "patient_identifier_id")
    private long patientIdentifierId;

    @ColumnInfo(name = "patient_id")
    @Json(name = "patient_id")
    private long patientId;

    @ColumnInfo(name = "identifier")
    @Json(name = "identifier")
    private String identifier;

    @ColumnInfo(name = "identifier_type")
    @Json(name = "identifier_type")
    private long identifierType;

    @ColumnInfo(name = "preferred")
    @Json(name = "preferred")
    private short preferred;

    @ColumnInfo(name = "location_id")
    @Json(name = "location_id")
    private long locationId;

    @ColumnInfo(name = "creator")
    @Json(name = "creator")
    private Long creator;

    @ColumnInfo(name = "date_created")
    @Json(name = "date_created")
    private LocalDateTime dateCreated;

    @ColumnInfo(name = "date_changed")
    @Json(name = "date_changed")
    private LocalDateTime dateChanged;

    @ColumnInfo(name = "changed_by")
    @Json(name = "changed_by")
    private Long changedBy;

    @ColumnInfo(name = "voided")
    @Json(name = "voided")
    private short voided;

    @ColumnInfo(name = "voided_by")
    @Json(name = "voided_by")
    private Long voidedBy;

    @ColumnInfo(name = "date_void")
    @Json(name = "date_void")
    private LocalDateTime dateVoid;

    @ColumnInfo(name = "void_reason")
    @Json(name = "void_reason")
    private String voidReason;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;


    public PatientIdentifierEntity(long patientIdentifierId, long patientId, String identifier, long identifierType , long locationId, LocalDateTime dateCreated){

        this.patientIdentifierId = patientIdentifierId;
        this.patientId = patientId;
        this.identifier = identifier;
        this.identifierType = identifierType;
        this.preferred = preferred;
        this.locationId = locationId;
        this.dateCreated = dateCreated;
        this.voided = 0;
    }

    @Ignore
    public PatientIdentifierEntity(long patientIdentifierId, long patientId, String identifier, long identifierType , short preferred, long locationId, LocalDateTime dateCreated){

        this(patientIdentifierId,patientId,identifier,identifierType,locationId,dateCreated);
        this.preferred = preferred;
    }

    public long getPatientIdentifierId() {
        return patientIdentifierId;
    }

    public void setPatientIdentifierId(long patientIdentifierId) {
        this.patientIdentifierId = patientIdentifierId;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public long getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(long identifierType) {
        this.identifierType = identifierType;
    }

    public short getPreferred() {
        return preferred;
    }

    public void setPreferred(short preferred) {
        this.preferred = preferred;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
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

    public LocalDateTime getDateVoid() {
        return dateVoid;
    }

    public void setDateVoid(LocalDateTime dateVoid) {
        this.dateVoid = dateVoid;
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
