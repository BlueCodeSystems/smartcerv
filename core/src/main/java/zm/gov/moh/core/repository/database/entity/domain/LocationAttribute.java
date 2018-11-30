package zm.gov.moh.core.repository.database.entity.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.ZonedDateTime;

@Entity(tableName = "location_attribute")
public class LocationAttribute {

    @PrimaryKey
    public Long location_attribute_id;
    public Long location_id;
    public Long attribute_type_id;
    public String value_reference;
    public String uuid;
    public Long creator;
    public ZonedDateTime date_created;
    public Long changed_by;
    public ZonedDateTime date_changed;
    public short voided;
    public Long voided_by;
    public ZonedDateTime date_voided;
    public String void_reason;
}