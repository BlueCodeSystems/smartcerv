package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZonedDateTime;

@Entity
public class Obs {

    @PrimaryKey
    public long obs_id;
    public long person_id;
    public long concept_id;
    public Long encounter_id;
    public Long order_id;
    public ZonedDateTime obs_datetime;
    public Long location_id;
    public Long obs_group_id;
    public String accession_number;
    public Long value_group_id;
    public Long value_coded;
    public Long value_coded_name_id;
    public Long value_drug;
    public ZonedDateTime value_datetime;
    public Double value_numeric;
    public String value_modifier;
    public String value_text;
    public String value_complex;
    public String comments;
    public long creator;
    public ZonedDateTime date_created;
    public short voided;
    public Long voided_by;
    public ZonedDateTime date_voided;
    public String void_reason;
    public String uuid;
    public Long previous_version;
    public String form_namespace_and_path;
    public String status;
    public String interpretation;

    public Obs(){

    }

    //Numeric obs
    @Ignore
    public Obs(long obs_id,long person_id,long concept_id,long encounter_id,ZonedDateTime obs_datetime,long location_id,Double value_numeric){

        this.obs_id = obs_id;
        this.person_id = person_id;
        this.concept_id = concept_id;
        this.encounter_id = encounter_id;
        this.obs_datetime = obs_datetime;
        this.location_id = location_id;
        this.value_numeric = value_numeric;
    }

    //Datetime obs
    @Ignore
    public Obs(long obs_id,long person_id,long concept_id,long encounter_id,ZonedDateTime obs_datetime,long location_id, ZonedDateTime value_datetime){

        this.obs_id = obs_id;
        this.person_id = person_id;
        this.concept_id = concept_id;
        this.encounter_id = encounter_id;
        this.obs_datetime = obs_datetime;
        this.location_id = location_id;
        this.value_datetime = value_datetime;
    }

    //Text obs
    @Ignore
    public Obs(long obs_id,long person_id,long concept_id,long encounter_id,ZonedDateTime obs_datetime,long location_id, String value_text){

        this.obs_id = obs_id;
        this.person_id = person_id;
        this.concept_id = concept_id;
        this.encounter_id = encounter_id;
        this.obs_datetime = obs_datetime;
        this.location_id = location_id;
        this.value_text = value_text;
    }

    //Coded obs
    @Ignore
    public Obs(long obs_id,long person_id,long concept_id,long encounter_id,ZonedDateTime obs_datetime,long location_id, long value_coded){

        this.obs_id = obs_id;
        this.person_id = person_id;
        this.concept_id = concept_id;
        this.encounter_id = encounter_id;
        this.obs_datetime = obs_datetime;
        this.location_id = location_id;
        this.value_coded = value_coded;
    }
}