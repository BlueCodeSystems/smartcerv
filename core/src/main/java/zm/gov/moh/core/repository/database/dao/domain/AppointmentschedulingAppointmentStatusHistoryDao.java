package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.AppointmentschedulingAppointmentStatusHistory;

@Dao
public interface AppointmentschedulingAppointmentStatusHistoryDao {

    @Query("SELECT * FROM appointmentscheduling_appointment_status_history")
    AppointmentschedulingAppointmentStatusHistory getAppointmentStatusHistory();

    @Insert
    public void insert(AppointmentschedulingAppointmentStatusHistory appointmentschedulingAppointmentStatusHistory);
}
