package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.AppointmentschedulingBlockTypeMap;

public interface AppointmentschedulingBlockTypeMapDao {

    @Query("SELECT * FROM appointmentscheduling_block_type_map")
    AppointmentschedulingBlockTypeMap getAppointmentBlockTypeMap();

    @Insert
    public void insert(AppointmentschedulingBlockTypeMap appointmentschedulingBlockTypeMap);
}
