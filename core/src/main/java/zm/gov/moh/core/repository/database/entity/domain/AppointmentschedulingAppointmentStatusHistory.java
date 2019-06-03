package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "appointmentscheduling_appointment_status_history")
public class AppointmentschedulingAppointmentStatusHistory {

    @PrimaryKey
    @ColumnInfo(name = "appointment_status_history_id")
    @Json(name = "appointment_status_history_id")
    private Long appointmentStatusHistoryId;

    @ColumnInfo(name = "appointment_id")
    @Json(name = "appointment_id")
    private Long appointmentId;

    @ColumnInfo(name = "status")
    @Json(name = "status")
    private String status;

    @ColumnInfo(name = "start_date")
    @Json(name = "start_date")
    private LocalDateTime startDate;

    @ColumnInfo(name = "end_date")
    @Json(name = "end_date")
    private LocalDateTime endDate;

    public Long getAppointmentStatusHistoryId() {
        return appointmentStatusHistoryId;
    }

    public void setAppointmentStatusHistoryId(Long appointmentStatusHistoryId) {
        this.appointmentStatusHistoryId = appointmentStatusHistoryId;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
