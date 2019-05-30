package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "appointmentscheduling_time_slot")
public class AppointmentschedulingTimeSlot {

    @PrimaryKey
    public Long time_slot_id;
    public Long appointment_block_id;
    public LocalDateTime start_date;
    public LocalDateTime end_date;
    public String uuid;
    public Integer creator;
    public LocalDateTime date_created;
    public Integer changed_by;
    public LocalDateTime date_changed;
    public Integer voided;
    public Integer voided_by;
    public LocalDateTime date_voided;
    public String void_reason;
}
