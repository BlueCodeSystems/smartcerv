package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;
import org.threeten.bp.ZonedDateTime;

@Entity
public class Visit {

    @PrimaryKey
    public long visit_id;
    public long patient_id;
    public long visit_type_id;
    public ZonedDateTime date_started;
    public ZonedDateTime date_stopped;
    public Long indication_concept_id;
    public Long location_id;
    public Long creator;
    public ZonedDateTime date_created;
    public Long changed_by;
    public ZonedDateTime date_changed;
    public short voided;
    public Long voided_by;
    public ZonedDateTime date_voided;
    public String void_reason;
    public String uuid;
}