package zm.gov.moh.core.repository.database.entity.derived;

import androidx.room.*;
import org.threeten.bp.LocalDateTime;

@Entity
public class Client {

    public long patient_id;
    public String identifier;
    public String given_name;
    public String family_name;
    public String gender;
    public LocalDateTime birthdate;
}