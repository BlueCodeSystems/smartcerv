package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "appointmentscheduling_appointment_request")
public class AppointmentschedulingAppointmentRequest {

    @PrimaryKey
    @ColumnInfo(name = "appointment_request_id")
    @Json(name = "appointment_request_id")
    private Long appointmentRequestId;

    @ColumnInfo(name = "patient_id")
    @Json(name = "patient_id")
    private Long patientId;

    @ColumnInfo(name = "appointment_type_id")
    @Json(name = "appointment_type_id")
    private Long appointmentTypeId;

    @ColumnInfo(name = "status")
    @Json(name = "status")
    private String status;

    @ColumnInfo(name = "provider_id")
    @Json(name = "provider_id")
    private Long providerId;

    @ColumnInfo(name = "requested_by")
    @Json(name = "requested_by")
    private Long requestedBy;

    @ColumnInfo(name = "requested_on")
    @Json(name = "requested_on")
    private LocalDateTime requestedOn;

    @ColumnInfo(name = "min_time_frame_value")
    @Json(name = "min_time_frame_value")
    private Integer minTimeFrameValue;

    @ColumnInfo(name = "min_time_frame_units")
    @Json(name = "min_time_frame_units")
    private String minTimeFrameUnits;

    @ColumnInfo(name = "max_time_frame_value")
    @Json(name = "max_time_frame_value")
    private Integer maxTimeFrameValue;

    @ColumnInfo(name = "max_time_frame_units")
    @Json(name = "max_time_frame_units")
    private String maxTimeFrameUnits;

    @ColumnInfo(name = "notes")
    @Json(name = "notes")
    private String notes;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    @ColumnInfo(name = "creator")
    @Json(name = "creator")
    private Integer creator;

    @ColumnInfo(name = "date_created")
    @Json(name = "date_created")
    private LocalDateTime dateCreated;

    @ColumnInfo(name = "changed_by")
    @Json(name = "changed_by")
    private Integer changedBy;

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

    public Long getAppointmentRequestId() {
        return appointmentRequestId;
    }

    public void setAppointmentRequestId(Long appointmentRequestId) {
        this.appointmentRequestId = appointmentRequestId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getAppointmentTypeId() {
        return appointmentTypeId;
    }

    public void setAppointmentTypeId(Long appointmentTypeId) {
        this.appointmentTypeId = appointmentTypeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Long getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(Long requestedBy) {
        this.requestedBy = requestedBy;
    }

    public LocalDateTime getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(LocalDateTime requestedOn) {
        this.requestedOn = requestedOn;
    }

    public Integer getMinTimeFrameValue() {
        return minTimeFrameValue;
    }

    public void setMinTimeFrameValue(Integer minTimeFrameValue) {
        this.minTimeFrameValue = minTimeFrameValue;
    }

    public String getMinTimeFrameUnits() {
        return minTimeFrameUnits;
    }

    public void setMinTimeFrameUnits(String minTimeFrameUnits) {
        this.minTimeFrameUnits = minTimeFrameUnits;
    }

    public Integer getMaxTimeFrameValue() {
        return maxTimeFrameValue;
    }

    public void setMaxTimeFrameValue(Integer maxTimeFrameValue) {
        this.maxTimeFrameValue = maxTimeFrameValue;
    }

    public String getMaxTimeFrameUnits() {
        return maxTimeFrameUnits;
    }

    public void setMaxTimeFrameUnits(String maxTimeFrameUnits) {
        this.maxTimeFrameUnits = maxTimeFrameUnits;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Integer changedBy) {
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
}
