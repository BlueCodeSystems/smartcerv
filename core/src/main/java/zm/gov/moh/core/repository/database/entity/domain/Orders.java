package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "orders")
public class Orders {

    @PrimaryKey
    @ColumnInfo(name = "order_id")
    @Json(name = "order_id")
    private Long orderId;

    @ColumnInfo(name = "order_type_id")
    @Json(name = "order_type_id")
    private Long orderTypeId;

    @ColumnInfo(name = "concept_id")
    @Json(name = "concept_id")
    public long conceptId;

    @ColumnInfo(name = "orderer")
    @Json(name = "orderer")
    private Long orderer;

    @ColumnInfo(name = "encounter_id")
    @Json(name = "encounter_id")
    private long encounterId;

    @ColumnInfo(name = "instructions")
    @Json(name = "instructions")
    private String instructions;

    @ColumnInfo(name = "date_activated")
    @Json(name = "date_activated")
    private LocalDateTime dateActivated;

    @ColumnInfo(name = "auto_expire_date")
    @Json(name = "auto_expire_date")
    private LocalDateTime autoExpireDate;

    @ColumnInfo(name = "date_stopped")
    @Json(name = "date_stopped")
    private LocalDateTime dateStopped;

    @ColumnInfo(name = "order_reason")
    @Json(name = "order_reason")
    private Long orderReason;

    @ColumnInfo(name = "order_reason_non_coded")
    @Json(name = "order_reason_non_coded")
    private String orderReasonNonCoded;

    @ColumnInfo(name = "creator")
    @Json(name = "creator")
    private Long creator;

    @ColumnInfo(name = "date_created")
    @Json(name = "date_created")
    private LocalDateTime dateCreated;

    @ColumnInfo(name = "voided")
    @Json(name = "voided")
    private Integer voided;

    @ColumnInfo(name = "voided_by")
    @Json(name = "voided_by")
    private Integer voidedBy;

    @ColumnInfo(name = "date_voided")
    @Json(name = "date_voided")
    private LocalDateTime dateVoided;

    @ColumnInfo(name = "void_reason")
    @Json(name = "void_reason")
    private String voidReason;

    @ColumnInfo(name = "patient_id")
    @Json(name = "patient_id")
    private Long patientId;

    @ColumnInfo(name = "accession_number")
    @Json(name = "accession_number")
    private String accessionNumber;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    @ColumnInfo(name = "urgency")
    @Json(name = "urgency")
    private String urgency;

    @ColumnInfo(name = "order_number")
    @Json(name = "order_number")
    private String orderNumber;

    @ColumnInfo(name = "previous_order_id")
    @Json(name = "previous_order_id")
    private Long previousOrderId;

    @ColumnInfo(name = "order_action")
    @Json(name = "order_action")
    private String orderAction;

    @ColumnInfo(name = "comment_to_fulfiller")
    @Json(name = "comment_to_fulfiller")
    private String commentTofulfiller;

    @ColumnInfo(name = "care_setting")
    @Json(name = "care_setting")
    private Integer careSettings;

    @ColumnInfo(name = "scheduled_date")
    @Json(name = "scheduled_date")
    private LocalDateTime scheduledDate;

    @ColumnInfo(name = "order_group_id")
    @Json(name = "order_group_id")
    private Integer orderGroupId;

    @ColumnInfo(name = "sort_weight")
    @Json(name = "sort_weight")
    private Double sortWeight;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public long getConceptId() {
        return conceptId;
    }

    public void setConceptId(long conceptId) {
        this.conceptId = conceptId;
    }

    public Long getOrderer() {
        return orderer;
    }

    public void setOrderer(Long orderer) {
        this.orderer = orderer;
    }

    public long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(long encounterId) {
        this.encounterId = encounterId;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public LocalDateTime getDateActivated() {
        return dateActivated;
    }

    public void setDateActivated(LocalDateTime dateActivated) {
        this.dateActivated = dateActivated;
    }

    public LocalDateTime getAutoExpireDate() {
        return autoExpireDate;
    }

    public void setAutoExpireDate(LocalDateTime autoExpireDate) {
        this.autoExpireDate = autoExpireDate;
    }

    public LocalDateTime getDateStopped() {
        return dateStopped;
    }

    public void setDateStopped(LocalDateTime dateStopped) {
        this.dateStopped = dateStopped;
    }

    public Long getOrderReason() {
        return orderReason;
    }

    public void setOrderReason(Long orderReason) {
        this.orderReason = orderReason;
    }

    public String getOrderReasonNonCoded() {
        return orderReasonNonCoded;
    }

    public void setOrderReasonNonCoded(String orderReasonNonCoded) {
        this.orderReasonNonCoded = orderReasonNonCoded;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getVoided() {
        return voided;
    }

    public void setVoided(Integer voided) {
        this.voided = voided;
    }

    public Integer getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Integer voidedBy) {
        this.voidedBy = voidedBy;
    }

    public LocalDateTime getDateVoided() {
        return dateVoided;
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

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getPreviousOrderId() {
        return previousOrderId;
    }

    public void setPreviousOrderId(Long previousOrderId) {
        this.previousOrderId = previousOrderId;
    }

    public String getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(String orderAction) {
        this.orderAction = orderAction;
    }

    public String getCommentTofulfiller() {
        return commentTofulfiller;
    }

    public void setCommentTofulfiller(String commentTofulfiller) {
        this.commentTofulfiller = commentTofulfiller;
    }

    public Integer getCareSettings() {
        return careSettings;
    }

    public void setCareSettings(Integer careSettings) {
        this.careSettings = careSettings;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Integer getOrderGroupId() {
        return orderGroupId;
    }

    public void setOrderGroupId(Integer orderGroupId) {
        this.orderGroupId = orderGroupId;
    }

    public Double getSortWeight() {
        return sortWeight;
    }

    public void setSortWeight(Double sortWeight) {
        this.sortWeight = sortWeight;
    }
}
