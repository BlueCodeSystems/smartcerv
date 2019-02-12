package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "encounter_role")
public class EncounterRole {

    @PrimaryKey
    public long encounter_role_id;
    public String name;
    public String description;
    public Long creator;
    public LocalDateTime date_created;
    public Long changed_by;
    public LocalDateTime date_changed;
    public short retired;
    public Long retired_by;
    public LocalDateTime date_retired;
    public String retire_reason;
    public String uuid;
}