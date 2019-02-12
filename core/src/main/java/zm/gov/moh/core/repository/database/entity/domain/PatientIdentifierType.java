package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;
import org.threeten.bp.LocalDateTime;

@Entity(tableName = "patient_identifier_type")
public class PatientIdentifierType {

    @PrimaryKey
    public Long patient_identifier_type_id;
    public String name;
    public String description;
    public String format;
    public short check_digit;
    public Long creator;
    public LocalDateTime date_created;
    public short required;
    public String format_description;
    public String validator;
    public String location_behavior;
    public short retired;
    public Long retired_by;
    public LocalDateTime date_retired;
    public String retired_reason;
    public String uuid;
    public String uniqueness_behavior;
    public LocalDateTime date_changed;
    public Long changed_by;
}
