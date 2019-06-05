package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

@Dao
public interface AppointmentschedulingAppointmentBlockDao {


    @Query("SELECT * FROM appointmentscheduling_appointment_block")
    AppointmentschedulingAppointmentBlockDao getAppointmentBlock();

    @Insert
    void insert(AppointmentschedulingAppointmentBlockDao appointmentschedulingAppointmentBlockDao);
}
