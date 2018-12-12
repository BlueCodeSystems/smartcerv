package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.ZonedDateTime;

@Entity(tableName = "visit_attribute_type")
public class VisitAttributeType {

    @PrimaryKey
    public long visit_attribute_type_id;
    public String name;
    public String description;
    public String datatype;
    public String datatype_config;
    public String preffered_handler;
    public String handler_config;
    public long min_occurs;
    public long max_occurs;
    public Long creator;
    public ZonedDateTime date_created;
    public Long changed_by;
    public ZonedDateTime date_changed;
    public short retired;
    public Long retired_by;
    public ZonedDateTime date_retired;
    public String retire_reason;
    public String uuid;
}