package zm.gov.moh.core.repository.database.entity.domain;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

@Entity
public class Person {

    @PrimaryKey
    public long person_id;
    public String gender;
    public LocalDate birthdate;
    public short birthdate_estimated;
    public short dead;
    public LocalDate death_date;
    public long cause_of_death;
    public long creator;
    public LocalDateTime date_created;
    public long changed_by;
    public LocalDateTime date_changed;
    public short voided_by;
    public LocalDateTime date_voided;
    public String void_reason;
    public String uuid;
    public short deathdate_estimated;
    public LocalDateTime birthtime;

    public Person(long person_id, LocalDate birthdate,String gender){

        this.person_id = person_id;
        this.birthdate = birthdate;
        this.gender = gender;
    }
}