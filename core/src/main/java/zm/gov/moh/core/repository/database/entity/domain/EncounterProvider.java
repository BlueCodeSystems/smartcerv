package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.ZonedDateTime;

@Entity(tableName = "encounter_provider")
public class EncounterProvider {

    @PrimaryKey
    public long encounter_provider_id;
    public long encounter_id;
    public long provider_id;
    public long encounter_role_id;
    public Long creator;
    public ZonedDateTime date_created;
    public Long changed_by;
    public ZonedDateTime date_changed;
    public short voided;
    public ZonedDateTime date_voided;
    public Long voided_by;
    public String void_reason;
    public String uuid;
}