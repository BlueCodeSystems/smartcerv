package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalDateTime;

@Entity(tableName = "person_attribute")
public class PersonAttribute {

    @PrimaryKey
    public long person_attribute_id;
    public long person_id;
    public String value;
    public long person_attribute_type_id;
    public long creator;
    public LocalDateTime date_created;
    public long changed_by;
    public LocalDateTime date_changed;
    public short voided;
    public long voided_by;
    public LocalDateTime date_voided;
    public String void_reason;
    public String uuid;
}
