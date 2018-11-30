package zm.gov.moh.core.repository.database.entity.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

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
}