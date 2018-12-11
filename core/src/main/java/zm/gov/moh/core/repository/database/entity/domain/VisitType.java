package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.ZonedDateTime;

@Entity(tableName = "visit_type")
public class VisitType {

    @PrimaryKey
    public long visit_type_id;
    public String name;
    public String description;
    public Long creator;
    public ZonedDateTime date_created;
    public Long changed_by;
    public ZonedDateTime date_changed;
    public short retired;
    public Long retired_by;
    public ZonedDateTime date_retired;
    public String retire_reason;
    public String uuid;
}