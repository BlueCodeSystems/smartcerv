package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

@Entity(tableName = "location_tag_map")
public class LocationTagMap {

    @PrimaryKey
    @ColumnInfo(name = "location_id")
    @Json(name = "location_id")
    private long locationId;

    @ColumnInfo(name = "location_tag_id")
    @Json(name = "location_tag_id")
    private long locationTagId;

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public long getLocationTagId() {
        return locationTagId;
    }

    public void setLocationTagId(long locationTagId) {
        this.locationTagId = locationTagId;
    }
}