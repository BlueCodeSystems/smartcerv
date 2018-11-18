package zm.gov.moh.core.repository.database.entity.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.ZonedDateTime;

@Entity
public class User {

    @PrimaryKey
    public Long user_id;
    public String system_id;
    public String username;
    @Ignore
    public String password;
    @Ignore
    public String salt;
    public String secret_question;
    public String secret_answer;
    public Long creator;
    public ZonedDateTime date_created;
    public Long changed_by;
    public ZonedDateTime date_changed;
    public Long person_id;
    public short retired;
    public Long retired_by;
    public ZonedDateTime date_retired;
    public String retired_reason;
    public String uuid;
}