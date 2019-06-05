package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalDateTime;

@Entity(tableName = "encounter_provider")
public class EncounterProvider {

    @PrimaryKey
    @ColumnInfo(name = "encounter_provider_id")
    @Json(name = "encounter_provider_id")
    private long encounterProviderId;

    @ColumnInfo(name = "encounter_id")
    @Json(name = "encounter_id")
    private long encounterId;

    @ColumnInfo(name = "provider_id")
    @Json(name = "provider_id")
    private long providerId;

    @ColumnInfo(name = "encounter_role_id")
    @Json(name = "encounter_role_id")
    private long encounterRoleId;

    @ColumnInfo(name = "creator")
    @Json(name = "creator")
    private Long creator;

    @ColumnInfo(name = "date_created")
    @Json(name = "date_created")
    private LocalDateTime date_created;

    @ColumnInfo(name = "changed_by")
    @Json(name = "changed_by")
    private Long changed_by;

    @ColumnInfo(name = "date_changed")
    @Json(name = "date_changed")
    private LocalDateTime date_changed;

    @ColumnInfo(name = "voided")
    @Json(name = "voided")
    private short voided;

    @ColumnInfo(name = "date_voided")
    @Json(name = "date_voided")
    private LocalDateTime date_voided;

    @ColumnInfo(name = "voided_by")
    @Json(name = "voided_by")
    private Long voided_by;

    @ColumnInfo(name = "void_reason")
    @Json(name = "void_reason")
    private String void_reason;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    public EncounterProvider(){ }

    @Ignore
    public EncounterProvider(long encounterProviderId, long encounterId, long providerId, Long encounterRoleId, long creator){

        this.encounterProviderId = encounterProviderId;
        this.encounterId = encounterId;
        this.providerId = providerId;
        this.encounterRoleId = encounterRoleId;
        this.creator = creator;
    }

    @Ignore
    public EncounterProvider(long encounterProviderId, long encounter_id, long providerId, long creator){

        this.encounterProviderId = encounterProviderId;
        this.encounterId = encounterId;
        this.providerId = providerId;
        this.creator = creator;
    }

    public long getEncounterProviderId() {
        return encounterProviderId;
    }

    public void setEncounterProviderId(long encounterProviderId) {
        this.encounterProviderId = encounterProviderId;
    }

    public long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(long encounterId) {
        this.encounterId = encounterId;
    }

    public long getProviderId() {
        return providerId;
    }

    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }

    public long getEncounterRoleId() {
        return encounterRoleId;
    }

    public void setEncounterRoleId(long encounterRoleId) {
        this.encounterRoleId = encounterRoleId;
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

    public short getVoided() {
        return voided;
    }

    public void setVoided(short voided) {
        this.voided = voided;
    }

    public LocalDateTime getDate_voided() {
        return date_voided;
    }

    public void setDate_voided(LocalDateTime date_voided) {
        this.date_voided = date_voided;
    }

    public Long getVoided_by() {
        return voided_by;
    }

    public void setVoided_by(Long voided_by) {
        this.voided_by = voided_by;
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