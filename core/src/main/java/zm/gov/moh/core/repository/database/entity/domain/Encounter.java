package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "encounter")
public class Encounter {

    @PrimaryKey
    public long encounter_id;
    public long encounter_type;
    public long patient_id;
    public long location_id;
    public long form_id;
    public LocalDateTime encounter_datetime;
    public Long creator;
    public LocalDateTime date_created;
    public short voided;
    public Long voided_by;
    public LocalDateTime date_voided;
    public String void_reason;
    public Long changed_by;
    public LocalDateTime date_changed;
    public long visit_id;
    public String uuid;

    public Encounter(){

    }

    @Ignore
    public Encounter(long encounter_id,long encounter_type, long patient_id, long location_id,long visit_id, long creator, LocalDateTime zonedDatetimeNow){

        this.encounter_id = encounter_id;
        this.encounter_type = encounter_type;
        this.patient_id = patient_id;
        this.location_id = location_id;
        this.visit_id = visit_id;
        this.creator = creator;
        this.encounter_datetime = zonedDatetimeNow;
        this.date_created = zonedDatetimeNow;
    }

    public long getEncounter_id() {
        return encounter_id;
    }

    public void setEncounter_id(long encounter_id) {
        this.encounter_id = encounter_id;
    }

    public long getEncounter_type() {
        return encounter_type;
    }

    public void setEncounter_type(long encounter_type) {
        this.encounter_type = encounter_type;
    }

    public long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(long patient_id) {
        this.patient_id = patient_id;
    }

    public long getLocation_id() {
        return location_id;
    }

    public void setLocation_id(long location_id) {
        this.location_id = location_id;
    }

    public long getForm_id() {
        return form_id;
    }

    public void setForm_id(long form_id) {
        this.form_id = form_id;
    }

    public LocalDateTime getEncounter_datetime() {
        return encounter_datetime;
    }

    public void setEncounter_datetime(LocalDateTime encounter_datetime) {
        this.encounter_datetime = encounter_datetime;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public LocalDateTime getDate_created() {
        return date_created;
    }

    public void setDate_created(LocalDateTime date_created) {
        this.date_created = date_created;
    }

    public short getVoided() {
        return voided;
    }

    public void setVoided(short voided) {
        this.voided = voided;
    }

    public Long getVoided_by() {
        return voided_by;
    }

    public void setVoided_by(Long voided_by) {
        this.voided_by = voided_by;
    }

    public LocalDateTime getDate_voided() {
        return date_voided;
    }

    public void setDate_voided(LocalDateTime date_voided) {
        this.date_voided = date_voided;
    }

    public String getVoid_reason() {
        return void_reason;
    }

    public void setVoid_reason(String void_reason) {
        this.void_reason = void_reason;
    }

    public Long getChanged_by() {
        return changed_by;
    }

    public void setChanged_by(Long changed_by) {
        this.changed_by = changed_by;
    }

    public LocalDateTime getDate_changed() {
        return date_changed;
    }

    public void setDate_changed(LocalDateTime date_changed) {
        this.date_changed = date_changed;
    }

    public long getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(long visit_id) {
        this.visit_id = visit_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
