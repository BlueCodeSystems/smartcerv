package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;
import zm.gov.moh.core.repository.database.entity.SynchronizableEntity;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "person_name")
public class PersonName extends SynchronizableEntity {

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
    public Long voided_by;
    public LocalDateTime date_voided;
    public String void_reason;
    public Long changed_by;
    public LocalDateTime date_changed;
    public String uuid;

    public PersonName(long person_id, String given_name, String family_name, short preferred){

        this.person_id = person_id;
        this.given_name = given_name;
        this.family_name = family_name;
        this.preferred = preferred;

    }

    @Ignore
    public PersonName(long person_name_id, long person_id, String given_name, String family_name, short preferred, LocalDateTime date_created){

        this.person_name_id = person_name_id;
        this.person_id = person_id;
        this.given_name = given_name;
        this.family_name = family_name;
        this.preferred = preferred;
        this.date_created = date_created;

    }

    @Override
    public long getId() {
        return person_name_id;
    }
}