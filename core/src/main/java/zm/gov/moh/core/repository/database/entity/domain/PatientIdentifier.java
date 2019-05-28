package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;
import zm.gov.moh.core.repository.database.entity.SynchronizableEntity;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalDateTime;

@Entity(tableName = "patient_identifier")
public class PatientIdentifier extends SynchronizableEntity {

    @PrimaryKey
    public long patient_identifier_id;
    public long patient_id;
    public String identifier;
    public long identifier_type;
    public short preferred;
    public long location_id;
    public Long creator;
    public LocalDateTime date_created;
    public LocalDateTime date_changed;
    public Long changed_by;
    public short voided;
    public Long voided_by;
    public LocalDateTime date_void;
    public String void_reason;
    public String uuid;


    public PatientIdentifier(long patient_identifier_id, long patient_id,String identifier,long identifier_type , long location_id, LocalDateTime date_created){

        this.patient_identifier_id = patient_identifier_id;
        this.patient_id = patient_id;
        this.identifier = identifier;
        this.identifier_type = identifier_type;
        this.preferred = preferred;
        this.location_id = location_id;
        this.date_created = date_created;
    }

    @Ignore
    public PatientIdentifier(long patient_identifier_id, long patient_id,String identifier,long identifier_type ,short preferred, long location_id, LocalDateTime date_created){

        this(patient_identifier_id,patient_id,identifier,identifier_type,location_id,date_created);
        this.preferred = preferred;
    }

    @Override
    public long getId() {
        return patient_identifier_id;
    }
}
