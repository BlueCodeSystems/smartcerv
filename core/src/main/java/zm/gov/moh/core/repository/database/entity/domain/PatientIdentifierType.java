package zm.gov.moh.core.repository.database.entity.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.ZonedDateTime;

@Entity(tableName = "patient_identifier_type")
public class PatientIdentifierType {

    @PrimaryKey
    public Long patient_identifier_type_id;
    public String name;
    public String description;
    public String format;
    public short check_digit;
    public Long creator;
    public ZonedDateTime date_created;
    public short required;
    public String format_description;
    public String validator;
    public String location_behavior;
    public short retired;
    public Long retired_by;
    public ZonedDateTime date_retired;
    public String retired_reason;
    public String uuid;
    public String uniqueness_behavior;
    public ZonedDateTime date_changed;
    public Long changed_by;
}
