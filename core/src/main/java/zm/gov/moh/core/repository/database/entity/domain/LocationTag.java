package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalDateTime;

@Entity(tableName = "location_tag")
public class LocationTag {

    @PrimaryKey
    public long location_tag_id;
    public String name;
    public String description;
    public Long creator;
    public LocalDateTime date_created;
    public short retired;
    public Long retired_by;
    public LocalDateTime date_retired;
    public String retire_reason;
    public String uuid;
    public Long changed_by;
    public LocalDateTime date_changed;
}
