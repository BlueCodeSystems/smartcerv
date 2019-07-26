package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.AppointmentschedulingAppointmentType;

@Dao
public interface AppointmentschedulingAppointmentTypeDao {

    @Query("SELECT * FROM appointmentscheduling_appointment_type;")
    List<AppointmentschedulingAppointmentType> getAppointmentTypes();

    @Insert
    void insert(AppointmentschedulingAppointmentType... appointmentschedulingAppointmentTypes);
}
