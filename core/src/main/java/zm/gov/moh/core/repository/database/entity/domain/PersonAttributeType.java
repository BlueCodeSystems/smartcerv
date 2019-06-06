package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalDateTime;

@Entity(tableName = "person_attribute_type")
public class PersonAttributeType {

    @PrimaryKey
    @ColumnInfo(name = "person_attribute_type_id")
    @Json(name = "person_attribute_type_id")
    private long personAttributeTypeId;

    @ColumnInfo(name = "name")
    @Json(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    @Json(name = "description")
    private String description;

    @ColumnInfo(name = "format")
    @Json(name = "format")
    private String format;

    @ColumnInfo(name = "foreign_key")
    @Json(name = "foreign_key")
    private long foreignKey;

    @ColumnInfo(name = "searchable")
    @Json(name = "searchable")
    private short searchable;

    @ColumnInfo(name = "creator")
    @Json(name = "creator")
    private long creator;

    @ColumnInfo(name = "date_created")
    @Json(name = "date_created")
    private LocalDateTime dateCreated;

    @ColumnInfo(name = "changed_by")
    @Json(name = "changed_by")
    private Long changedBy;

    @ColumnInfo(name = "date_changed")
    @Json(name = "date_changed")
    private LocalDateTime dateChanged;

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

    @ColumnInfo(name = "edit_privilege")
    @Json(name = "edit_privilege")
    private String editPrivilege;

    @ColumnInfo(name = "sort_weight")
    @Json(name = "sort_weight")
    private double sortWeight;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    public long getPersonAttributeTypeId() {
        return personAttributeTypeId;
    }

    public void setPersonAttributeTypeId(long personAttributeTypeId) {
        this.personAttributeTypeId = personAttributeTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public long getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(long foreignKey) {
        this.foreignKey = foreignKey;
    }

    public short getSearchable() {
        return searchable;
    }

    public void setSearchable(short searchable) {
        this.searchable = searchable;
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

    public Long getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Long changedBy) {
        this.changedBy = changedBy;
    }

    public LocalDateTime getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(LocalDateTime dateChanged) {
        this.dateChanged = dateChanged;
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

    public String getEditPrivilege() {
        return editPrivilege;
    }

    public void setEditPrivilege(String editPrivilege) {
        this.editPrivilege = editPrivilege;
    }

    public double getSortWeight() {
        return sortWeight;
    }

    public void setSortWeight(double sortWeight) {
        this.sortWeight = sortWeight;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}