package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "relationship_type")
public class RelationshipType {

    @PrimaryKey
    @ColumnInfo(name = "relationship_type_id")
    @Json(name = "relationship_type_id")
    private long relationshipTypeId;

    @ColumnInfo(name = "a_is_to_b")
    @Json(name = "a_is_to_b")
    private String a_is_to_b;

    @ColumnInfo(name = "b_is_to_a")
    @Json(name = "b_is_to_a")
    private String b_is_to_a;

    @ColumnInfo(name = "preferred")
    @Json(name = "preferred")
    private short preferred;

    @ColumnInfo(name = "weight")
    @Json(name = "weight")
    private Integer weight;

    @ColumnInfo(name = "description")
    @Json(name = "description")
    private String description;

    @ColumnInfo(name = "creator")
    @Json(name = "creator")
    private Long creator;

    @ColumnInfo(name = "date_created")
    @Json(name = "date_created")
    private LocalDateTime dateCreated;

    @ColumnInfo(name = "retired")
    @Json(name = "retired")
    private short retired;

    @ColumnInfo(name = "retired_by")
    @Json(name = "retired_by")
    private Long retiredBy;

    @ColumnInfo(name = "date_retired")
    @Json(name = "date_retired")
    private LocalDateTime dateRetired;

    @ColumnInfo(name = "retire_reason")
    @Json(name = "retire_reason")
    private String retireReason;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    @ColumnInfo(name = "date_changed")
    @Json(name = "date_changed")
    private LocalDateTime dateChanged;

    @ColumnInfo(name = "changed_by")
    @Json(name = "changed_by")
    private Long changedBy;

    public long getRelationshipTypeId() {
        return relationshipTypeId;
    }

    public void setRelationshipTypeId(long relationshipTypeId) {
        this.relationshipTypeId = relationshipTypeId;
    }

    public String getA_is_to_b() {
        return a_is_to_b;
    }

    public void setA_is_to_b(String a_is_to_b) {
        this.a_is_to_b = a_is_to_b;
    }

    public String getB_is_to_a() {
        return b_is_to_a;
    }

    public void setB_is_to_a(String b_is_to_a) {
        this.b_is_to_a = b_is_to_a;
    }

    public short getPreferred() {
        return preferred;
    }

    public void setPreferred(short preferred) {
        this.preferred = preferred;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public short getRetired() {
        return retired;
    }

    public void setRetired(short retired) {
        this.retired = retired;
    }

    public Long getRetiredBy() {
        return retiredBy;
    }

    public void setRetiredBy(Long retiredBy) {
        this.retiredBy = retiredBy;
    }

    public LocalDateTime getDateRetired() {
        return dateRetired;
    }

    public void setDateRetired(LocalDateTime dateRetired) {
        this.dateRetired = dateRetired;
    }

    public String getRetireReason() {
        return retireReason;
    }

    public void setRetireReason(String retireReason) {
        this.retireReason = retireReason;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(LocalDateTime dateChanged) {
        this.dateChanged = dateChanged;
    }

    public Long getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Long changedBy) {
        this.changedBy = changedBy;
    }
}
