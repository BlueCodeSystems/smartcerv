package zm.gov.moh.core.repository.database.entity.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.LocalDateTime;

@Entity
public class PatientIdentifier {

    @PrimaryKey
    public long patient_identifier_id;
    public long patient_id;
    public String identifier;
    public long identifier_type;
    public short preferred;
    public long location_id;
    public long creator;
    public LocalDateTime date_created;
    public LocalDateTime date_changed;
    public long changed_by;
    public short voided;
    public long voided_by;
    public LocalDateTime date_void;
    public String void_reason;
    public String uuid;
}
