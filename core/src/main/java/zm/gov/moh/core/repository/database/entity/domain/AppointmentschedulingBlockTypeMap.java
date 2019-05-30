package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.Entity;

@Entity(tableName = "appointmentscheduling_block_type_map")
public class AppointmentschedulingBlockTypeMap {

    public Long appointment_type_id;
    public Long appointment_block_id;
}
