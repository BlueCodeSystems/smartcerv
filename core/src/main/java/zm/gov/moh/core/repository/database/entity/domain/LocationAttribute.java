package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalDateTime;

@Entity(tableName = "location_attribute")
public class LocationAttribute {

    @PrimaryKey
    public Long location_attribute_id;
    public Long location_id;
    public Long attribute_type_id;
    public String value_reference;
    public String uuid;
    public Long creator;
    public LocalDateTime date_created;
    public Long changed_by;
    public LocalDateTime date_changed;
    public short voided;
    public Long voided_by;
    public LocalDateTime date_voided;
    public String void_reason;
}