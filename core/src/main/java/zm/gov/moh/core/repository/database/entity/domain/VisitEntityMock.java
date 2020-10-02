package zm.gov.moh.core.repository.database.entity.domain;

import com.squareup.moshi.Json;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import zm.gov.moh.core.repository.database.entity.SynchronizableEntity;

@Entity(tableName = "visit_mock")
public class VisitEntityMock extends SynchronizableEntity {

    @PrimaryKey
    @ColumnInfo(name = "visit_id")
    @Json(name = "visit_id")
    private Long visitId;

    @ColumnInfo(name = "patient_id")
    @Json(name = "patient_id")
    private long patientId;

    @ColumnInfo(name = "visit_type_id")
    @Json(name = "visit_type_id")
    private long visitTypeId;

    @ColumnInfo(name = "indication_concept_id")
    @Json(name = "indication_concept_id")
    private Long indicationConceptId;

    @ColumnInfo(name = "location_id")
    @Json(name = "location_id")
    private Long locationId;

    @ColumnInfo(name = "creator")
    @Json(name = "creator")
    private Long creator;

    @ColumnInfo(name = "changed_by")
    @Json(name = "changed_by")
    private Long changedBy;

    @ColumnInfo(name = "voided")
    @Json(name = "voided")
    private short voided;

    @ColumnInfo(name = "voided_by")
    @Json(name = "voided_by")
    private Long voidedBy;

    @ColumnInfo(name = "void_reason")
    @Json(name = "void_reason")
    private String voidReason;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    public VisitEntityMock(){

    }

    @Ignore
    public VisitEntityMock(long visitId, long visitTypeId, long patientId, Long locationId, Long creator){

        this.visitId = visitId;
        this.visitTypeId = visitTypeId;
        this.patientId = patientId;
        this.locationId = locationId;
        this.creator = creator;
    }

    //Setters and Getters
    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getVisitTypeId() {
        return visitTypeId;
    }

    public void setVisitTypeId(long visitTypeId) {
        this.visitTypeId = visitTypeId;
    }

    public Long getIndicationConceptId() {
        return indicationConceptId;
    }

    public void setIndicationConceptId(Long indicationConceptId) {
        this.indicationConceptId = indicationConceptId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Long changedBy) {
        this.changedBy = changedBy;
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

    public void setVoidedBy(Long voidedBy) {
        this.voidedBy = voidedBy;
    }

    public String getVoidReason() {
        return voidReason;
    }

    public void setVoidReason(String voidReason) {
        this.voidReason = voidReason;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public long getId() {
        return visitId;
    }
}