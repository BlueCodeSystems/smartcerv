package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "appointmentscheduling_appointment")
public class AppointmentschedulingAppointment {

    @PrimaryKey
    public Long appointment_id;
    public Long time_slot_id;
    public Long visit_id;
    public Long patient_id;
    public Long appointment_type_id;
    public String status;
    public String reason;
    public String uuid;
    public Long creator;
    public LocalDateTime date_created;
    public Long changed_by;
    public LocalDateTime date_changed;
    public Integer voided;
    public Integer voided_by;
    public LocalDateTime date_voided;
    public String void_reason;
    public String cancel_reason;
}
