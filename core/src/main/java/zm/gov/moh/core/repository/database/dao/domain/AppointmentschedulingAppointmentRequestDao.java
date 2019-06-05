package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.AppointmentschedulingAppointmentRequest;

@Dao
public interface AppointmentschedulingAppointmentRequestDao {

    @Query("SELECT * FROM appointmentscheduling_appointment_request")
    AppointmentschedulingAppointmentRequest getAppointmentRequest();

    @Insert
    void insert(AppointmentschedulingAppointmentRequest appointmentschedulingAppointmentRequest);
}
