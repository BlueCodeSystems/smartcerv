package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "appointmentscheduling_appointment_block")
public class AppointmentschedulingAppointmentBlock {

    @PrimaryKey
    public Long appointment_block_id;
    public Long location_id;
    public Long provider_id;
    public LocalDateTime start_date;
    public LocalDateTime end_date;
    public String uuid;
    public Integer creator;
    public LocalDateTime date_created;
    public Integer changed_by;
    public LocalDateTime date_changed;
    public Integer voided_by;
    public LocalDateTime date_voided;
    public String void_reason;
}
