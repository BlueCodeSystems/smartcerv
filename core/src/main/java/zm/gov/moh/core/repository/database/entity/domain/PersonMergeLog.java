package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "person_merge_log")
public class PersonMergeLog {

    @PrimaryKey
    @ColumnInfo(name = "person_merge_log_id")
    @Json(name = "person_merge_log_id")
    private Long personMergeLogId;

    @ColumnInfo(name = "winner_person_id")
    @Json(name = "winner_person_id")
    private Long winnerPersonId;

    @ColumnInfo(name = "loser_person_id")
    @Json(name = "loser_person_id")
    private Long loserPersonId;

    @ColumnInfo(name = "creator")
    @Json(name = "creator")
    private Long creator;

    @ColumnInfo(name = "date_created")
    @Json(name = "date_created")
    private LocalDateTime dateCreated;

    @ColumnInfo(name = "merged_data")
    @Json(name = "merged_data")
    private String merged_data;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    @ColumnInfo(name = "changed_by")
    @Json(name = "changed_by")
    private Long changedBy;

    @ColumnInfo(name = "date_changed")
    @Json(name = "date_changed")
    private LocalDateTime dateChanged;

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

    public Long getPersonMergeLogId() {
        return personMergeLogId;
    }

    public void setPersonMergeLogId(Long personMergeLogId) {
        this.personMergeLogId = personMergeLogId;
    }

    public Long getWinnerPersonId() {
        return winnerPersonId;
    }

    public void setWinnerPersonId(Long winnerPersonId) {
        this.winnerPersonId = winnerPersonId;
    }

    public Long getLoserPersonId() {
        return loserPersonId;
    }

    public void setLoserPersonId(Long loserPersonId) {
        this.loserPersonId = loserPersonId;
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

    public String getMerged_data() {
        return merged_data;
    }

    public void setMerged_data(String merged_data) {
        this.merged_data = merged_data;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
}
