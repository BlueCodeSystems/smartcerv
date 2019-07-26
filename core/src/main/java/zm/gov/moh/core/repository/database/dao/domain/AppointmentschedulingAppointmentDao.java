package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.AppointmentschedulingAppointment;

@Dao
public interface AppointmentschedulingAppointmentDao {

    @Query("SELECT * FROM appointmentscheduling_appointment")
    AppointmentschedulingAppointment getAppointment();

    @Insert
    void insert(AppointmentschedulingAppointment appointmentschedulingAppointment);
}
