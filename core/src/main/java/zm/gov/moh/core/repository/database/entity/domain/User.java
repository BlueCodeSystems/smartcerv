package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
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
    public LocalDateTime date_created;
    public Long changed_by;
    public LocalDateTime date_changed;
    public Long person_id;
    public short retired;
    public Long retired_by;
    public LocalDateTime date_retired;
    public String retired_reason;
    public String uuid;
}