package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "drug")
public class Drug {

    @PrimaryKey
    public long drug_id;
    public long concept_id;
    public String name;
    public Short combination;
    public Long dosage_form;
    public Double maximum_daily_dosage;
    public Double minimum_daily_dosage;
    public Long route;
    public Long creator;
    public LocalDateTime date_created;
    public Short retired;
    public Long changed_by;
    public LocalDateTime date_changed;
    public Long retired_by;
    public String retired_reason;
    public String uuid;
    public String strength;

}
