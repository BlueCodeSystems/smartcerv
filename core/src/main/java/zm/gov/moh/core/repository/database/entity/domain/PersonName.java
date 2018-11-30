package zm.gov.moh.core.repository.database.entity.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
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
    public ZonedDateTime date_created;
    public short voided;
    public Long voided_by;
    public ZonedDateTime date_voided;
    public String void_reason;
    public Long changed_by;
    public ZonedDateTime date_changed;
    public String uuid;

    public PersonName(long person_id, String given_name, String family_name, short preferred){

        this.person_id = person_id;
        this.given_name = given_name;
        this.family_name = family_name;
        this.preferred = preferred;

    }

    @Ignore
    public PersonName(long person_name_id, long person_id, String given_name, String family_name, short preferred){

        this.person_name_id = person_name_id;
        this.person_id = person_id;
        this.given_name = given_name;
        this.family_name = family_name;
        this.preferred = preferred;

    }
}