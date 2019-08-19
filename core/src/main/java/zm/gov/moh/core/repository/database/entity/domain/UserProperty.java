package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

@Entity(tableName = "user_property")
public class UserProperty {

    @PrimaryKey
    @ColumnInfo(name = "user_id")
    @Json(name = "user_id")
    private long userId;

    @ColumnInfo(name = "property")
    @Json(name = "property")
    private String property;

    @ColumnInfo(name = "property_value")
    @Json(name = "property_value")
    private String propertyValue;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
}
