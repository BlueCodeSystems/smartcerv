package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.AppointmentschedulingTimeSlot;

@Dao
public interface AppointmentschedulingTimeSlotDao {

    @Query("SELECT * FROM appointmentscheduling_time_slot;")
    AppointmentschedulingTimeSlot getSchedulingTimeSlot();

    @Insert
    void insert(AppointmentschedulingTimeSlot appointmentschedulingTimeSlot);
}
