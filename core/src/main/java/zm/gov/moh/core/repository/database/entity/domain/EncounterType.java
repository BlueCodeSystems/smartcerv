package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;
import org.threeten.bp.ZonedDateTime;

@Entity(tableName = "encounter_type")
public class EncounterType {

    @PrimaryKey
    public long encounter_type_id;
    public String name;
    public String description;
    public Long creator;
    public ZonedDateTime date_created;
    public short retired;
    public Long retired_by;
    public ZonedDateTime date_retired;
    public String retire_reason;
    public String uuid;
    public String edit_privilege;
    public String view_privilege;
    public Long changed_by;
    public ZonedDateTime date_changed;
}