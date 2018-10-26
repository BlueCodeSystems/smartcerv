package zm.gov.moh.core.repository.database.entity.derived;

import android.arch.persistence.room.Dao;

import java.util.Date;

@Dao
public class Client {

    long person_id;
    private String given_name;
    private String family_name;
    private String gender;
    private Date birthdate;

    public Client(final long person_id, final String given_name, final String family_name, final String gender, final Date birthdate){

    }
}