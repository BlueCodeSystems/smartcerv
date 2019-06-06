package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "location_attribute_type")
public class LocationAttributeType{

    @PrimaryKey
    @ColumnInfo(name = "location_attribute_type_id")
    @Json(name = "location_attribute_type_id")
    private Long locationAttributeTypeId;

    @ColumnInfo(name = "name")
    @Json(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    @Json(name = "description")
    private String description;

    @ColumnInfo(name = "datatype")
    @Json(name = "datatype")
    private String datatype;

    @ColumnInfo(name = "datatype_config")
    @Json(name = "datatype_config")
    private String datatypeConfig;

    @ColumnInfo(name = "preferred_handler")
    @Json(name = "preferred_handler")
    private String preferredHandler;

    @ColumnInfo(name = "handler_config")
    @Json(name = "handler_config")
    private String handlerConfig;

    @ColumnInfo(name = "min_occurs")
    @Json(name = "min_occurs")
    private Long minOccurs;

    @ColumnInfo(name = "max_occurs")
    @Json(name = "max_occurs")
    private Long maxOccurs;

    @ColumnInfo(name = "creator")
    @Json(name = "creator")
    private Long creator;

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

    @ColumnInfo(name = "retired_reason")
    @Json(name = "retired_reason")
    private String retiredReason;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    public Long getLocationAttributeTypeId() {
        return locationAttributeTypeId;
    }

    public void setLocationAttributeTypeId(Long locationAttributeTypeId) {
        this.locationAttributeTypeId = locationAttributeTypeId;
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

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getDatatypeConfig() {
        return datatypeConfig;
    }

    public void setDatatypeConfig(String datatypeConfig) {
        this.datatypeConfig = datatypeConfig;
    }

    public String getPreferredHandler() {
        return preferredHandler;
    }

    public void setPreferredHandler(String preferredHandler) {
        this.preferredHandler = preferredHandler;
    }

    public String getHandlerConfig() {
        return handlerConfig;
    }

    public void setHandlerConfig(String handlerConfig) {
        this.handlerConfig = handlerConfig;
    }

    public Long getMinOccurs() {
        return minOccurs;
    }

    public void setMinOccurs(Long minOccurs) {
        this.minOccurs = minOccurs;
    }

    public Long getMaxOccurs() {
        return maxOccurs;
    }

    public void setMaxOccurs(Long maxOccurs) {
        this.maxOccurs = maxOccurs;
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

    public String getRetiredReason() {
        return retiredReason;
    }

    public void setRetiredReason(String retiredReason) {
        this.retiredReason = retiredReason;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}