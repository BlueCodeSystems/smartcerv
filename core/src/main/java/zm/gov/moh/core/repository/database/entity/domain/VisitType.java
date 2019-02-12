package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "visit_type")
public class VisitType {

    @PrimaryKey
    public long visit_type_id;
    public String name;
    public String description;
    public Long creator;
    public LocalDateTime date_created;
    public Long changed_by;
    public LocalDateTime date_changed;
    public short retired;
    public Long retired_by;
    public LocalDateTime date_retired;
    public String retire_reason;
    public String uuid;
}