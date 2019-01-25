package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
public class Obs {

    @PrimaryKey
    public long obs_id;
    public long person_id;
    public long concept_id;
    public Long encounter_id;
    public Long order_id;
    public ZonedDateTime obs_datetime;
    public Long location_id;
    public Long obs_group_id;
    public String accession_number;
    public Long value_group_id;
    public Long value_coded;
    public Long value_coded_name_id;
    public Long value_drug;
    public ZonedDateTime value_datetime;
    public Double value_numeric;
    public String value_modifier;
    public String value_text;
    public String value_complex;
    public String comments;
    public long creator;
    public ZonedDateTime date_created;
    public short voided;
    public Long voided_by;
    public ZonedDateTime date_voided;
    public String void_reason;
    public String uuid;
    public Long previous_version;
    public String form_namespace_and_path;
    public String status;
    public String interpretation;

    public Obs(){

    }

    @Ignore
    public Obs(long obs_id,long person_id,long encounter_id,ZonedDateTime obs_datetime,long location_id,long creator){

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

    public Obs setValue(ZonedDateTime value_datetime){

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
}