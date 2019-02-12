package zm.gov.moh.core.repository.database.entity.domain;

import org.threeten.bp.LocalDateTime;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "concept_name")
public class ConceptName {

    @PrimaryKey
    public long concept_name_id;
    public long concept_id;
    public String name;
    public String locale;
    public short locale_preferred;
    public Long creator;
    public LocalDateTime date_created;
    public String concept_name_type;
    public short voided;
    public Long voided_by;
    public LocalDateTime date_voided;
    public String void_reason;
    public String uuid;
    public LocalDateTime date_changed;
    public Long changed_by;
}

