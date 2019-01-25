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

    public Visit(){

    }

    @Ignore
    public Visit(long visit_id, long visit_type_id, long patient_id, Long location_id, Long creator,ZonedDateTime date_started){

        this.visit_id = visit_id;
        this.visit_type_id = visit_type_id;
        this.patient_id = patient_id;
        this.location_id = location_id;
        this.creator = creator;
        this.date_started = date_started;
        this.date_created = date_started;
    }

    //Setters and Getters
    public long getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(long visit_id) {
        this.visit_id = visit_id;
    }

    public long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(long patient_id) {
        this.patient_id = patient_id;
    }

    public long getVisit_type_id() {
        return visit_type_id;
    }

    public void setVisit_type_id(long visit_type_id) {
        this.visit_type_id = visit_type_id;
    }

    public ZonedDateTime getDate_started() {
        return date_started;
    }

    public void setDate_started(ZonedDateTime date_started) {
        this.date_started = date_started;
    }

    public ZonedDateTime getDate_stopped() {
        return date_stopped;
    }

    public void setDate_stopped(ZonedDateTime date_stopped) {
        this.date_stopped = date_stopped;
    }

    public Long getIndication_concept_id() {
        return indication_concept_id;
    }

    public void setIndication_concept_id(Long indication_concept_id) {
        this.indication_concept_id = indication_concept_id;
    }

    public Long getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Long location_id) {
        this.location_id = location_id;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public ZonedDateTime getDate_created() {
        return date_created;
    }

    public void setDate_created(ZonedDateTime date_created) {
        this.date_created = date_created;
    }

    public Long getChanged_by() {
        return changed_by;
    }

    public void setChanged_by(Long changed_by) {
        this.changed_by = changed_by;
    }

    public ZonedDateTime getDate_changed() {
        return date_changed;
    }

    public void setDate_changed(ZonedDateTime date_changed) {
        this.date_changed = date_changed;
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

    public ZonedDateTime getDate_voided() {
        return date_voided;
    }

    public void setDate_voided(ZonedDateTime date_voided) {
        this.date_voided = date_voided;
    }

    public String getVoid_reason() {
        return void_reason;
    }

    public void setVoid_reason(String void_reason) {
        this.void_reason = void_reason;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}