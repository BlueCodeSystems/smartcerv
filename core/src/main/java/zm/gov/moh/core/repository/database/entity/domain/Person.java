package zm.gov.moh.core.repository.database.entity.domain;


import androidx.room.*;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalDateTime;

@Entity
public class Person {

    @PrimaryKey
    public long person_id;
    public String gender;
    public LocalDateTime birthdate;
    public short birthdate_estimated;
    public short dead;
    public LocalDate death_date;
    public Long cause_of_death;
    public Long creator;
    public LocalDateTime date_created;
    public Long changed_by;
    public LocalDateTime date_changed;
    public Long voided_by;
    public LocalDateTime date_voided;
    public String void_reason;
    public String uuid;
    public short deathdate_estimated;
    public LocalDateTime birthtime;

    public Person(long person_id, LocalDateTime birthdate,String gender){

        this.person_id = person_id;
        this.birthdate = birthdate;
        this.gender = gender;
    }
}