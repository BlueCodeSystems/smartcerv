package zm.gov.moh.core.repository.database.entity.derived;

import androidx.room.*;

import com.squareup.moshi.Json;

@Entity
public class FacilityDistrictCode {

    @ColumnInfo(name = "location_id")
    @Json(name = "location_id")
    private String locationId;

    @ColumnInfo(name = "facility_code")
    @Json(name = "facility_code")
    private String facilityCode;

    @ColumnInfo(name = "district_code")
    @Json(name = "district_code")
    private String districtCode;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }
}