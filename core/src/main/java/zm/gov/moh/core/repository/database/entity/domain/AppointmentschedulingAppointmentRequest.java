package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "appointmentscheduling_appointment_request")
public class AppointmentschedulingAppointmentRequest {

    @PrimaryKey
    public Long appointment_request_id;
    public Long patient_id;
    public Long appointment_type_id;
    public String status;
    public Long provider_id;
    public Long requested_by;
    public LocalDateTime requested_on;
    public Integer min_time_frame_value;
    public String min_time_frame_units;
    public Integer max_time_frame_value;
    public String max_time_frame_units;
    public String notes;
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
