package zm.gov.moh.core.repository.database.entity.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "location_tag")
public class LocationTag {

    @PrimaryKey
    public long location_tag_id;
    public String name;
    public String description;
    public long creator;
    public LocalDateTime date_created;
    public short retired;
    public long retired_by;
    public LocalDateTime date_retired;
    public String retire_reason;
    public String uuid;
    public long changed_by;
    public LocalDateTime date_changed;
}
