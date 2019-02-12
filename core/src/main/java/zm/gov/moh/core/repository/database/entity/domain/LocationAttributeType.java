package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "location_attribute_type")
public class LocationAttributeType{

    @PrimaryKey
    public Long location_attribute_type_id;
    public String name;
    public String description;
    public String datatype;
    public String datatype_config;
    public String preferred_handler;
    public String handler_config;
    public Long min_occurs;
    public Long max_occurs;
    public Long creator;
    public LocalDateTime date_created;
    public Long changed_by;
    public LocalDateTime date_changed;
    public short retired;
    public Long retired_by;
    public LocalDateTime date_retired;
    public String retired_reason;
    public String uuid;
}