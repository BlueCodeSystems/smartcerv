package zm.gov.moh.core.repository.database.entity.domain;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZonedDateTime;

@Entity
public class Person {

    @PrimaryKey
    public long person_id;
    public String gender;
    public ZonedDateTime birthdate;
    public short birthdate_estimated;
    public short dead;
    public LocalDate death_date;
    public Long cause_of_death;
    public Long creator;
    public ZonedDateTime date_created;
    public Long changed_by;
    public ZonedDateTime date_changed;
    public Long voided_by;
    public ZonedDateTime date_voided;
    public String void_reason;
    public String uuid;
    public short deathdate_estimated;
    public ZonedDateTime birthtime;

    public Person(long person_id, ZonedDateTime birthdate,String gender){

        this.person_id = person_id;
        this.birthdate = birthdate;
        this.gender = gender;
    }
}