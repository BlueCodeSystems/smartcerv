package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.AppointmentschedulingAppointmentBlock;

@Dao
public interface AppointmentschedulingAppointmentBlockDao {


    @Query("SELECT * FROM appointmentscheduling_appointment_block")
    AppointmentschedulingAppointmentBlock getAppointmentBlock();

    @Insert
    void insert(AppointmentschedulingAppointmentBlock appointmentschedulingAppointmentBlockDao);
}
