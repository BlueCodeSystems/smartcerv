package zm.gov.moh.core.repository.database.entity.domain;

import org.threeten.bp.LocalDateTime;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import zm.gov.moh.core.repository.database.entity.SynchronizableEntity;

@Entity
public class Obs extends SynchronizableEntity {

    @PrimaryKey
    public long obs_id;
    public long person_id;
    public long concept_id;
    public Long encounter_id;
    public Long order_id;
    public LocalDateTime obs_datetime;
    public Long location_id;
    public Long obs_group_id;
    public String accession_number;
    public Long value_group_id;
    public Long value_coded;
    public Long value_coded_name_id;
    public Long value_drug;
    public LocalDateTime value_datetime;
    public Double value_numeric;
    public String value_modifier;
    public String value_text;
    public String value_complex;
    public String comments;
    public long creator;
    public LocalDateTime date_created;
    public short voided;
    public Long voided_by;
    public LocalDateTime date_voided;
    public String void_reason;
    public String uuid;
    public Long previous_version;
    public String form_namespace_and_path;
    public String status;
    public String interpretation;

    public Obs(){

    }

    @Ignore
    public Obs(long obs_id,long person_id,long encounter_id,LocalDateTime obs_datetime,long location_id,long creator){

        this.obs_id = obs_id;
        this.person_id = person_id;
        this.encounter_id = encounter_id;
        this.obs_datetime = obs_datetime;
        this.date_created = obs_datetime;
        this.location_id = location_id;
        this.creator = creator;
    }

    public Obs setConceptId(long concept_id){

        this.concept_id = concept_id;
        return this;
    }

    public Obs setObsGroupId(long obs_group_id){

        this.obs_group_id = obs_group_id;
        return this;
    }

    public Obs setValue(Double value_numeric){

        this.value_numeric = value_numeric;
        return this;
    }

    public Obs setValue(LocalDateTime value_datetime){

        this.value_datetime = value_datetime;
        return this;
    }


    public Obs setValue(String value_text){

        this.value_text = value_text;
        return this;
    }

    public Obs setValue(long value_coded){

        this.value_coded = value_coded;
        return this;
    }

    public List<Obs> setValue(Set<Long> answerConcepts){

        List<Obs> obsList = new LinkedList<>();

        if(answerConcepts.size() == 1) {

            this.setValue(answerConcepts.iterator().next());
            obsList.add(this);
        }
        else if(answerConcepts.size() > 1)
            for(Long conceptId : answerConcepts) {
                Obs obs = new Obs(obs_id++, person_id, encounter_id, obs_datetime, location_id, creator)
                        .setConceptId(concept_id)
                        .setValue(conceptId);
                obsList.add(obs);
            }

        return obsList;
    }

    //Setters and getters
    public long getObs_id() {
        return obs_id;
    }

    public void setObs_id(long obs_id) {
        this.obs_id = obs_id;
    }

    public long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(long person_id) {
        this.person_id = person_id;
    }

    public long getConcept_id() {
        return concept_id;
    }

    public void setConcept_id(long concept_id) {
        this.concept_id = concept_id;
    }

    public Long getEncounter_id() {
        return encounter_id;
    }

    public void setEncounter_id(Long encounter_id) {
        this.encounter_id = encounter_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public LocalDateTime getObs_datetime() {
        return obs_datetime;
    }

    public void setObs_datetime(LocalDateTime obs_datetime) {
        this.obs_datetime = obs_datetime;
    }

    public Long getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Long location_id) {
        this.location_id = location_id;
    }

    public Long getObs_group_id() {
        return obs_group_id;
    }

    public void setObs_group_id(Long obs_group_id) {
        this.obs_group_id = obs_group_id;
    }

    public String getAccession_number() {
        return accession_number;
    }

    public void setAccession_number(String accession_number) {
        this.accession_number = accession_number;
    }

    public Long getValue_group_id() {
        return value_group_id;
    }

    public void setValue_group_id(Long value_group_id) {
        this.value_group_id = value_group_id;
    }

    public Long getValue_coded() {
        return value_coded;
    }

    public void setValue_coded(Long value_coded) {
        this.value_coded = value_coded;
    }

    public Long getValue_coded_name_id() {
        return value_coded_name_id;
    }

    public void setValue_coded_name_id(Long value_coded_name_id) {
        this.value_coded_name_id = value_coded_name_id;
    }

    public Long getValue_drug() {
        return value_drug;
    }

    public void setValue_drug(Long value_drug) {
        this.value_drug = value_drug;
    }

    public LocalDateTime getValue_datetime() {
        return value_datetime;
    }

    public void setValue_datetime(LocalDateTime value_datetime) {
        this.value_datetime = value_datetime;
    }

    public Double getValue_numeric() {
        return value_numeric;
    }

    public void setValue_numeric(Double value_numeric) {
        this.value_numeric = value_numeric;
    }

    public String getValue_modifier() {
        return value_modifier;
    }

    public void setValue_modifier(String value_modifier) {
        this.value_modifier = value_modifier;
    }

    public String getValue_text() {
        return value_text;
    }

    public void setValue_text(String value_text) {
        this.value_text = value_text;
    }

    public String getValue_complex() {
        return value_complex;
    }

    public void setValue_complex(String value_complex) {
        this.value_complex = value_complex;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getPrevious_version() {
        return previous_version;
    }

    public void setPrevious_version(Long previous_version) {
        this.previous_version = previous_version;
    }

    public String getForm_namespace_and_path() {
        return form_namespace_and_path;
    }

    public void setForm_namespace_and_path(String form_namespace_and_path) {
        this.form_namespace_and_path = form_namespace_and_path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInterpretation() {
        return interpretation;
    }

    public void setInterpretation(String interpretation) {
        this.interpretation = interpretation;
    }

    @Override
    public long getId() {
        return obs_id;
    }
}