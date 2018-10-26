package zm.gov.moh.core.repository.database.entity.domain;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.ZonedDateTime;

@Entity
public class Person {

    @PrimaryKey
    public long person_id;
    public String gender;
    public ZonedDateTime birthdate;
    public short birthdate_estimated;
    public short dead;
    public ZonedDateTime death_date;
    public long cause_of_death;
    public long creator;
    public ZonedDateTime date_created;
    public long changed_by;
    public ZonedDateTime date_changed;
    public short voided_by;
    public ZonedDateTime date_voided;
    public String void_reason;
    public String uuid;
    public short deathdate_estimated;
    public ZonedDateTime birthtime;
}