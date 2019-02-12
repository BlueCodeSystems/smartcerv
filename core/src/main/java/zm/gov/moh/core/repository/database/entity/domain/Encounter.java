package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "encounter")
public class Encounter {

    @PrimaryKey
    public long encounter_id;
    public long encounter_type;
    public long patient_id;
    public long location_id;
    public long form_id;
    public LocalDateTime encounter_datetime;
    public Long creator;
    public LocalDateTime date_created;
    public short voided;
    public Long voided_by;
    public LocalDateTime date_voided;
    public String void_reason;
    public Long changed_by;
    public LocalDateTime date_changed;
    public long visit_id;
    public String uuid;

    public Encounter(){

    }

    @Ignore
    public Encounter(long encounter_id,long encounter_type, long patient_id, long location_id,long visit_id, long creator, LocalDateTime zonedDatetimeNow){

        this.encounter_id = encounter_id;
        this.encounter_type = encounter_type;
        this.patient_id = patient_id;
        this.location_id = location_id;
        this.visit_id = visit_id;
        this.creator = creator;
        this.encounter_datetime = zonedDatetimeNow;
        this.date_created = zonedDatetimeNow;
    }
}
