package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

@Entity(tableName = "appointmentscheduling_block_type_map")
public class AppointmentschedulingBlockTypeMap {

    @PrimaryKey
    @ColumnInfo(name = "appointment_type_id")
    @Json(name = "appointment_type_id")
    private Long appointmentTypeId;

    @ColumnInfo(name = "appointment_block_id")
    @Json(name = "appointment_block_id")
    private Long appointmentBlockId;

    public Long getAppointmentTypeId() {
        return appointmentTypeId;
    }

    public void setAppointmentTypeId(Long appointmentTypeId) {
        this.appointmentTypeId = appointmentTypeId;
    }

    public Long getAppointmentBlockId() {
        return appointmentBlockId;
    }

    public void setAppointmentBlockId(Long appointmentBlockId) {
        this.appointmentBlockId = appointmentBlockId;
    }
}
