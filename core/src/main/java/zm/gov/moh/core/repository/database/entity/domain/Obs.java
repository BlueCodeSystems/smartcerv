package zm.gov.moh.core.repository.database.entity.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.LocalDateTime;

@Entity
public class Obs {

    @PrimaryKey
    public long obs_id;
    public long person_id;
    public long concept_id;
    public long encounter_id;
    public long order_id;
    public LocalDateTime obs_datetime;
    public long location_id;
    public long obs_group_id;
    public String accession_number;
    public long value_group_id;
    public long value_coded;
    public long value_coded_name_id;
    public long value_drug;
    public LocalDateTime value_datetime;
    public double value_numeric;
    public String value_modifier;
    public String value_text;
    public String value_complex;
    public String comments;
    public long creator;
    public LocalDateTime date_created;
    public short voided;
    public long voided_by;
    public LocalDateTime date_voided;
    public String void_reason;
    public String uuid;
    public long previous_version;
    public String form_namespace_and_path;
    public String status;
    public String interpretation;
}