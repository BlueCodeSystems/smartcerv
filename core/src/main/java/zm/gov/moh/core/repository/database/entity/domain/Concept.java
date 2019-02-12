package zm.gov.moh.core.repository.database.entity.domain;

import org.threeten.bp.LocalDateTime;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Concept {

    @PrimaryKey
    public long concept_id;
    private short retired;
    private String short_name;
    private String description;
    private String form_text;
    private Long datatype_id;
    private Long class_id;
    private short is_set;
    private Long creator;
    private LocalDateTime date_created;
    private String version;
    private Long changed_by;
    private LocalDateTime date_changed;
    private Long retired_by;
    private LocalDateTime date_retired;
    private String retired_reason;
    private String uuid;

    public long getConcept_id() {
        return concept_id;
    }

    public void setConcept_id(long concept_id) {
        this.concept_id = concept_id;
    }

    public short getRetired() {
        return retired;
    }

    public void setRetired(short retired) {
        this.retired = retired;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getForm_text() {
        return form_text;
    }

    public void setForm_text(String form_text) {
        this.form_text = form_text;
    }

    public Long getDatatype_id() {
        return datatype_id;
    }

    public void setDatatype_id(Long datatype_id) {
        this.datatype_id = datatype_id;
    }

    public Long getClass_id() {
        return class_id;
    }

    public void setClass_id(Long class_id) {
        this.class_id = class_id;
    }

    public short getIs_set() {
        return is_set;
    }

    public void setIs_set(short is_set) {
        this.is_set = is_set;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public Long getRetired_by() {
        return retired_by;
    }

    public void setRetired_by(Long retired_by) {
        this.retired_by = retired_by;
    }

    public LocalDateTime getDate_retired() {
        return date_retired;
    }

    public void setDate_retired(LocalDateTime date_retired) {
        this.date_retired = date_retired;
    }

    public String getRetired_reason() {
        return retired_reason;
    }

    public void setRetired_reason(String retired_reason) {
        this.retired_reason = retired_reason;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}