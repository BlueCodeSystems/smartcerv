package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "appointmentscheduling_appointment_type")
public class AppointmentschedulingAppointmentType {

    @PrimaryKey
    public Long appointment_type_id;
    public String name;
    public String description;
    public Integer duration;
    public String uuid;
    public Integer creator;
    public LocalDateTime date_created;
    public Integer changed_by;
    public LocalDateTime date_changed;
    public Integer retired;
    public Integer retired_by;
    public LocalDateTime date_retired;
    public String retired_reason;
    public Integer confidential;
}
