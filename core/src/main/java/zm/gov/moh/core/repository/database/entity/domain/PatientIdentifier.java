package zm.gov.moh.core.repository.database.entity.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZonedDateTime;

@Entity(tableName = "patient_identifier")
public class PatientIdentifier {

    @PrimaryKey
    public long patient_identifier_id;
    public long patient_id;
    public String identifier;
    public long identifier_type;
    public short preferred;
    public long location_id;
    public Long creator;
    public ZonedDateTime date_created;
    public ZonedDateTime date_changed;
    public Long changed_by;
    public short voided;
    public Long voided_by;
    public ZonedDateTime date_void;
    public String void_reason;
    public String uuid;
}
