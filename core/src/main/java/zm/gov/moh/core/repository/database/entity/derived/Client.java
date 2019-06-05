package zm.gov.moh.core.repository.database.entity.derived;

import org.threeten.bp.LocalDateTime;

import androidx.room.Entity;

@Entity
public class Client {
    public long patient_id;
    public String identifier;
    public String given_name;
    public String family_name;
    public String gender;
    public LocalDateTime birthdate;
}