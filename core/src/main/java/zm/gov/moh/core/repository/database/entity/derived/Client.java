package zm.gov.moh.core.repository.database.entity.derived;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZonedDateTime;

@Entity
public class Client {

    public long patient_id;
    public String identifier;
    public String given_name;
    public String family_name;
    public String gender;
    public ZonedDateTime birthdate;
}