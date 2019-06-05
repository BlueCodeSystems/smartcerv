package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "appointmentscheduling_time_slot")
public class AppointmentschedulingTimeSlot {

    @PrimaryKey
    @ColumnInfo(name = "time_slot_id")
    @Json(name = "time_slot_id")
    private Long timeSlotId;

    @ColumnInfo(name = "appointment_block_id")
    @Json(name = "appointment_block_id")
    private Long appointmentBlockId;

    @ColumnInfo(name = "start_date")
    @Json(name = "start_date")
    private LocalDateTime startDate;

    @ColumnInfo(name = "end_date")
    @Json(name = "end_date")
    private LocalDateTime endDate;

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

    public Long getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Long timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public Long getAppointmentBlockId() {
        return appointmentBlockId;
    }

    public void setAppointmentBlockId(Long appointmentBlockId) {
        this.appointmentBlockId = appointmentBlockId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
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
