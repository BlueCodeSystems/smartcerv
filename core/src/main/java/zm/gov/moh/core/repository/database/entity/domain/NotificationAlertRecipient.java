package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "notification_alert_recipient")
public class NotificationAlertRecipient {

    @ColumnInfo(name = "alert_id")
    @Json(name = "alert_id")
    private Long alertId;

    @ColumnInfo(name = "user_id")
    @Json(name = "user_id")
    private Long userId;

    @ColumnInfo(name = "alert_read")
    @Json(name = "alert_read")
    private short alertRead;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public short getAlertRead() {
        return alertRead;
    }

    public void setAlertRead(short alertRead) {
        this.alertRead = alertRead;
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
