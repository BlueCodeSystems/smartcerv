package zm.gov.moh.core.repository.database.entity.domain;

import org.threeten.bp.LocalDateTime;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import zm.gov.moh.core.repository.database.entity.SynchronizableEntity;
import androidx.room.*;

import com.squareup.moshi.Json;

// implement Serializable to translate  object state into a format that can be stored
@Entity
public class Obs extends SynchronizableEntity {


    @PrimaryKey
    @ColumnInfo(name = "obs_id")
    @Json(name = "obs_id")
    private long obsId;

    @ColumnInfo(name = "person_id")
    @Json(name = "person_id")
    private long personId;

    @ColumnInfo(name = "concept_id")
    @Json(name = "concept_id")
    private long conceptId;

    @ColumnInfo(name = "encounter_id")
    @Json(name = "encounter_id")
    private Long encounterId;

    @ColumnInfo(name = "order_id")
    @Json(name = "order_id")
    private Long orderId;

    @ColumnInfo(name = "obs_datetime")
    @Json(name = "obs_datetime")
    private LocalDateTime obsDateTime;

    @ColumnInfo(name = "location_id")
    @Json(name = "location_id")
    private Long locationId;

    @ColumnInfo(name = "obs_group_id")
    @Json(name = "obs_group_id")
    private Long obsGroupId;

    @ColumnInfo(name = "accession_number")
    @Json(name = "accession_number")
    private String accessionNumber;

    @ColumnInfo(name = "value_group_id")
    @Json(name = "value_group_id")
    private Long valueGroupId;

    @ColumnInfo(name = "value_coded")
    @Json(name = "value_coded")
    private Long valueCoded;

    @ColumnInfo(name = "value_coded_name_id")
    @Json(name = "value_coded_name_id")
    private Long valueCodedNameId;

    @ColumnInfo(name = "value_drug")
    @Json(name = "value_drug")
    private Long valueDrug;

    @ColumnInfo(name = "value_datetime")
    @Json(name = "value_datetime")
    private LocalDateTime valueDateTime;

    @ColumnInfo(name = "value_numeric")
    @Json(name = "value_numeric")
    private Double valueNumeric;

    @ColumnInfo(name = "value_modifier")
    @Json(name = "value_modifier")
    private String valueModifier;

    @ColumnInfo(name = "value_text")
    @Json(name = "value_text")
    private String valueText;

    @ColumnInfo(name = "value_complex")
    @Json(name = "value_complex")
    private String valueComplex;

    @ColumnInfo(name = "comments")
    @Json(name = "comments")
    private String comments;

    @ColumnInfo(name = "creator")
    @Json(name = "creator")
    private long creator;

    @ColumnInfo(name = "date_created")
    @Json(name = "date_created")
    private LocalDateTime dateCreated;

    @ColumnInfo(name = "voided")
    @Json(name = "voided")
    private short voided;

    @ColumnInfo(name = "voided_by")
    @Json(name = "voided_by")
    private Long voidedBy;

    @ColumnInfo(name = "date_voided")
    @Json(name = "date_voided")
    private LocalDateTime dateVoided;

    @ColumnInfo(name = "void_reason")
    @Json(name = "void_reason")
    private String voidReason;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    @ColumnInfo(name = "previous_version")
    @Json(name = "previous_version")
    private Long previousVersion;

    @ColumnInfo(name = "form_namespace_and_path")
    @Json(name = "form_namespace_and_path")
    private String formNamespaceAndPath;

    @ColumnInfo(name = "status")
    @Json(name = "status")
    private String status;

    @ColumnInfo(name = "interpretation")
    @Json(name = "interpretation")
    private String interpretation;

    public Obs(){

    }

    @Ignore
    public Obs(long obsId,long personId,long encounterId,LocalDateTime obsDateTime,long locationId,long creator){

        this.obsId = obsId;
        this.personId = personId;
        this.encounterId = encounterId;
        this.obsDateTime = obsDateTime;
        this.dateCreated = obsDateTime;
        this.locationId = locationId;
        this.creator = creator;
    }

    public Obs setObsConceptId(long conceptId){

        this.conceptId = conceptId;
        return this;
    }

    public Obs setObsGroupId(long obsGroupId){

        this.obsGroupId = obsGroupId;
        return this;
    }

    public Obs setValue(Double valueNumeric){

        this.valueNumeric = valueNumeric;
        return this;
    }

    public Obs setValue(LocalDateTime valueDateTime){

        this.valueDateTime = valueDateTime;
        return this;
    }


    public Obs setValue(String valueText){

        this.valueText = valueText;
        return this;
    }

    public Obs setValue(long valueCoded){

        this.valueCoded = valueCoded;
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
                Obs obs = new Obs(obsId++, personId, encounterId, obsDateTime, locationId, creator)
                        .setObsConceptId(this.conceptId)
                        .setValue(conceptId);
                obsList.add(obs);
            }

        return obsList;
    }

    //Setters and getters
    public long getObsId() {
        return obsId;
    }

    public void setObsId(long obsId) {
        this.obsId = obsId;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public long getConceptId() {
        return conceptId;
    }

    public void setConceptId(long conceptId) {
        this.conceptId = conceptId;
    }

    public Long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Long encounterId) {
        this.encounterId = encounterId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getObsDateTime() {
        return obsDateTime;
    }

    public void setObsDateTime(LocalDateTime obsDateTime) {
        this.obsDateTime = obsDateTime;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocation_id(Long locationId) {
        this.locationId = locationId;
    }

    public Long getObsGroupId() {
        return obsGroupId;
    }

    public void setObsGroupId(Long obsGroupId) {
        this.obsGroupId = obsGroupId;
    }

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public Long getValueGroupId() {
        return valueGroupId;
    }

    public void setValueGroupId(Long valueGroupId) {
        this.valueGroupId = valueGroupId;
    }

    public Long getValueCoded() {
        return valueCoded;
    }

    public void setValueCoded(Long valueCoded) {
        this.valueCoded = valueCoded;
    }

    public Long getValueCodedNameId() {
        return valueCodedNameId;
    }

    public void setValueCodedNameId(Long valueCodedNameId) {
        this.valueCodedNameId = valueCodedNameId;
    }

    public Long getValueDrug() {
        return valueDrug;
    }

    public void setValueDrug(Long valueDrug) {
        this.valueDrug = valueDrug;
    }

    public LocalDateTime getValueDateTime() {
        return valueDateTime;
    }

    public void setValueDateTime(LocalDateTime valueDateTime) {
        this.valueDateTime = valueDateTime;
    }

    public Double getValueNumeric() {
        return valueNumeric;
    }

    public void setValueNumeric(Double valueNumeric) {
        this.valueNumeric = valueNumeric;
    }

    public String getValue_modifier() {
        return valueModifier;
    }

    public void setValueModifier(String valueModifier) {
        this.valueModifier = valueModifier;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    public String getValueComplex() {
        return valueComplex;
    }

    public void setValueComplex(String valueComplex) {
        this.valueComplex = valueComplex;
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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public short getVoided() {
        return voided;
    }

    public void setVoided(short voided) {
        this.voided = voided;
    }

    public Long getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Long voided_by) {
        this.voidedBy = voidedBy;
    }

    public LocalDateTime getDateVoided() {
        return dateVoided;
    }

    public void setDate_voided(LocalDateTime dateVoided) {
        this.dateVoided = dateVoided;
    }

    public String getVoid_reason() {
        return voidReason;
    }

    public void setVoid_reason(String voidReason) {
        this.voidReason = voidReason;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getPreviousVersion() {
        return previousVersion;
    }

    public void setPreviousVersion(Long previousVersion) {
        this.previousVersion = previousVersion;
    }

    public String getFormNamespaceAndPath() {
        return formNamespaceAndPath;
    }

    public void setFormNamespaceAndPath(String formNamespaceAndPath) {
        this.formNamespaceAndPath = formNamespaceAndPath;
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

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getValueModifier() {
        return valueModifier;
    }

    public void setDateVoided(LocalDateTime dateVoided) {
        this.dateVoided = dateVoided;
    }

    public String getVoidReason() {
        return voidReason;
    }

    public void setVoidReason(String voidReason) {
        this.voidReason = voidReason;
    }

    @Override
    public long getId() {
        return obsId;
    }
}