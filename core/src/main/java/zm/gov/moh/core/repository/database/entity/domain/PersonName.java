package zm.gov.moh.core.repository.database.entity.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZonedDateTime;

@Entity(tableName = "person_name")
public class PersonName {

    @PrimaryKey(autoGenerate = true)
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
    public LocalDateTime date_created;
    public short voided;
    public long voided_by;
    public LocalDateTime date_voided;
    public String void_reason;
    public long changed_by;
    public LocalDateTime date_changed;
    public String uuid;

    public PersonName(long person_id, String given_name, String family_name){

        this.person_id = person_id;
        this.given_name = given_name;
        this.family_name = family_name;

    }
}