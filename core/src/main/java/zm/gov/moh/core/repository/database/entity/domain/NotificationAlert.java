package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "notification_alert")
public class NotificationAlert {

    @PrimaryKey
    @ColumnInfo(name = "alert_id")
    @Json(name = "alert_id")
    private Long alertId;

    @ColumnInfo(name = "text")
    @Json(name = "text")
    private String text;

    @ColumnInfo(name = "satisfied_by_any")
    @Json(name = "satisfied_by_any")
    private short satisfiedByAny;

    @ColumnInfo(name = "alert_read")
    @Json(name = "alert_read")
    private short alertRead;

    @ColumnInfo(name = "date_to_expire")
    @Json(name = "date_to_expire")
    private LocalDateTime dateToExpire;

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

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public short getSatisfiedByAny() {
        return satisfiedByAny;
    }

    public void setSatisfiedByAny(short satisfiedByAny) {
        this.satisfiedByAny = satisfiedByAny;
    }

    public short getAlertRead() {
        return alertRead;
    }

    public void setAlertRead(short alertRead) {
        this.alertRead = alertRead;
    }

    public LocalDateTime getDateToExpire() {
        return dateToExpire;
    }

    public void setDateToExpire(LocalDateTime dateToExpire) {
        this.dateToExpire = dateToExpire;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
