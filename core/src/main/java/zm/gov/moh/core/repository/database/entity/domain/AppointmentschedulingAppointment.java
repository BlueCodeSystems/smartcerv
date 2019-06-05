package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "appointmentscheduling_appointment")
public class AppointmentschedulingAppointment {

    @PrimaryKey
    @ColumnInfo(name = "appointment_id") //name here should same as in Json
    @Json(name = "appointment_id")
    private Long appointmentId;

    @ColumnInfo(name = "time_slot_id")
    @Json(name = "time_slot_id")
    private Long timeSlotId;

    @ColumnInfo(name = "visit_id")
    @Json(name = "visit_id")
    private Long visitId;

    @ColumnInfo(name = "patient_id")
    @Json(name = "patient_id")
    private Long patientId;

    @ColumnInfo(name = "appointment_type_id")
    @Json(name = "appointment_type_id")
    private Long appointmentTypeId;

    @ColumnInfo(name = "status")
    @Json(name = "status")
    private String status;

    @ColumnInfo(name = "reason")
    @Json(name = "reason")
    private String reason;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

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

    @ColumnInfo(name = "cancel_reason")
    @Json(name = "cancel_reason")
    private String cancelReason;

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Long timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
