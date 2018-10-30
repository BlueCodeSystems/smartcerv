package zm.gov.moh.core.repository.database.entity.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZonedDateTime;

@Entity(tableName = "person_attribute_type")
public class PersonAttributeType {

    @PrimaryKey
    public long person_attribute_type_id;
    public String name;
    public String description;
    public String format;
    public long foreign_key;
    public short searchable;
    public long creator;
    public LocalDateTime date_created;
    public long changed_by;
    public LocalDateTime date_changed;
    public short retired;
    public long retired_by;
    public LocalDateTime date_retired;
    public String retire_reason;
    public String edit_privilege;
    public double sort_weight;
    public String uuid;
}