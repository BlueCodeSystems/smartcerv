package zm.gov.moh.core.repository.database.entity.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.ZonedDateTime;

@Entity(tableName = "person_attribute")
public class PersonAttribute {

    @PrimaryKey
    public long person_attribute_id;
    public long person_id;
    public String value;
    public long person_attribute_type_id;
    public long creator;
    public ZonedDateTime date_created;
    public long changed_by;
    public ZonedDateTime date_changed;
    public short voided;
    public long voided_by;
    public ZonedDateTime date_voided;
    public String void_reason;
    public String uuid;
}
