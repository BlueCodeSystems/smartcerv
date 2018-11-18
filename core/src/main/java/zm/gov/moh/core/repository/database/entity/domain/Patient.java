package zm.gov.moh.core.repository.database.entity.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZonedDateTime;

@Entity
public class Patient {

    @PrimaryKey
    public long patient_id;
    public Long creator;
    public ZonedDateTime date_created;
    public Long changed_by;
    public ZonedDateTime date_changed;
    public short voided;
    public Long voided_by;
    public ZonedDateTime date_voided;
    public String void_reason;
    public String allergy_status;

    public Patient(long patient_id, ZonedDateTime date_created){

        this.patient_id = patient_id;
        this.date_created = date_created;
    }
}