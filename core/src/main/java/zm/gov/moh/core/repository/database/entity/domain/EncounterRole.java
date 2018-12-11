package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.ZonedDateTime;

@Entity(tableName = "encounter_role")
public class EncounterRole {

    @PrimaryKey
    public long encounter_role_id;
    public String name;
    public String description;
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