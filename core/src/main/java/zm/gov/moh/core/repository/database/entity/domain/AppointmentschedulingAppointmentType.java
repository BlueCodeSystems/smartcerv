package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "appointmentscheduling_appointment_type")
public class AppointmentschedulingAppointmentType {

    @PrimaryKey
    @ColumnInfo(name = "appointment_type_id")
    @Json(name = "appointment_type_id")
    private Long appointmentTypeId;

    @ColumnInfo(name = "name")
    @Json(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    @Json(name = "description")
    private String description;

    @ColumnInfo(name = "duration")
    @Json(name = "duration")
    private Integer duration;

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

    @ColumnInfo(name = "retired")
    @Json(name = "retired")
    private Integer retired;

    @ColumnInfo(name = "retired_by")
    @Json(name = "retired_by")
    private Integer retiredBy;

    @ColumnInfo(name = "date_retired")
    @Json(name = "date_retired")
    private LocalDateTime dateRetired;

    @ColumnInfo(name = "retired_reason")
    @Json(name = "retired_reason")
    private String retiredReason;

    @ColumnInfo(name = "confidential")
    @Json(name = "confidential")
    private Integer confidential;

    public Long getAppointmentTypeId() {
        return appointmentTypeId;
    }

    public void setAppointmentTypeId(Long appointmentTypeId) {
        this.appointmentTypeId = appointmentTypeId;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public Integer getRetired() {
        return retired;
    }

    public void setRetired(Integer retired) {
        this.retired = retired;
    }

    public Integer getRetiredBy() {
        return retiredBy;
    }

    public void setRetiredBy(Integer retiredBy) {
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

    public Integer getConfidential() {
        return confidential;
    }

    public void setConfidential(Integer confidential) {
        this.confidential = confidential;
    }
}
