package zm.gov.moh.core.repository.database.entity.derived;

import android.arch.persistence.room.Dao;

import org.threeten.bp.LocalDate;

import java.util.Date;

@Dao
public class Client {

    public long person_id;
    public String given_name;
    public String family_name;
    public String gender;
    public LocalDate birthdate;

}