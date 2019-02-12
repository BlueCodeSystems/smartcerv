package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalDateTime;

@Entity(tableName = "encounter_provider")
public class EncounterProvider {

    @PrimaryKey
    public long encounter_provider_id;
    public long encounter_id;
    public long provider_id;
    public long encounter_role_id;
    public Long creator;
    public LocalDateTime date_created;
    public Long changed_by;
    public LocalDateTime date_changed;
    public short voided;
    public LocalDateTime


            date_voided;
    public Long voided_by;
    public String void_reason;
    public String uuid;

    public EncounterProvider(){ }

    @Ignore
    public EncounterProvider(long encounter_provider_id, long encounter_id, long provider_id, Long encounter_role_id, long creator){

        this.encounter_provider_id = encounter_provider_id;
        this.encounter_id = encounter_id;
        this.provider_id = provider_id;
        this.encounter_role_id = encounter_role_id;
        this.creator = creator;
    }

    @Ignore
    public EncounterProvider(long encounter_provider_id, long encounter_id, long provider_id, long creator){

        this.encounter_provider_id = encounter_provider_id;
        this.encounter_id = encounter_id;
        this.provider_id = provider_id;
        this.creator = creator;
    }

}