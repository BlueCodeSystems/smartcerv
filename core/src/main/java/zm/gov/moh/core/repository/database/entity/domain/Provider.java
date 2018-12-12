package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.ZonedDateTime;

@Entity
public class Provider {

    @PrimaryKey
    public Long provider_id;
    public Long person_id;
    public String name;
    public String identifier;
    public Long creator;
    public ZonedDateTime date_created;
    public Long changed_by;
    public ZonedDateTime date_changed;
    public short retired;
    public Long retired_by;
    public ZonedDateTime date_retired;
    public String retired_reason;
    public String uuid;
    public Long provider_role_id;
}