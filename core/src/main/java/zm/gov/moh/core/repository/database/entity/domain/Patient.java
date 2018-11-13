package zm.gov.moh.core.repository.database.entity.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.LocalDateTime;

@Entity
public class Patient {

    @PrimaryKey
    public long patient_id;
    public long creator;
    public LocalDateTime date_created;
    public long changed_by;
    public LocalDateTime date_changed;
    public short voided;
    public long voided_by;
    public LocalDateTime date_voided;
    public String void_reason;
    public String allergy_status;
}
