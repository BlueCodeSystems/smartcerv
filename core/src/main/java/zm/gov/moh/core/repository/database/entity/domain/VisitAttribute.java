package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;
import org.threeten.bp.ZonedDateTime;

@Entity(tableName = "visit_attribute")
public class VisitAttribute {

    @PrimaryKey
    public long visit_attribute_id;
    public long visit_id;
    public long attribute_type_id;
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