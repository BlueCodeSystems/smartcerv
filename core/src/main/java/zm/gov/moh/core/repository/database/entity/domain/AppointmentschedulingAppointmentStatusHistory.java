package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "appointmentscheduling_appointment_status_history")
public class AppointmentschedulingAppointmentStatusHistory {

    @PrimaryKey
    public Long appointment_status_history_id;
    public Long appointment_id;
    public String status;
    public LocalDateTime start_date;
    public LocalDateTime end_date;
}
