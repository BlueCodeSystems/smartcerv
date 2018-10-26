package zm.gov.moh.core.repository.database.entity.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.ZonedDateTime;

@Entity(tableName = "person_name")
public class PersonName {

    @PrimaryKey
    public long person_name_id;
    public short preferred;
    public long person_id;
    public String prefix;
    public String given_name;
    public String middle_name;
    public String family_name_prefix;
    public String family_name;
    public String family_name2;
    public String family_name_suffix;
    public String degree;
    public long creator;
    public ZonedDateTime date_created;
    public short voided;
    public long voided_by;
    public ZonedDateTime date_voided;
    public String void_reason;
    public long changed_by;
    public ZonedDateTime date_changed;
    public String uuid;
}