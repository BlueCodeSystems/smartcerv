package zm.gov.moh.core.repository.database.dao.derived;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import zm.gov.moh.core.repository.database.entity.derived.FacilityDistrictCode;
import zm.gov.moh.core.repository.database.entity.domain.LocationAttribute;

@Dao
public interface FacilityDistrictCodeDao {

    @Query("SELECT facility_code.location_id AS location_id, district_code, facility_code FROM (SELECT location_id,value_reference AS district_code FROM location_attribute WHERE attribute_type_id = 4) district_code JOIN (SELECT location_id,value_reference AS facility_code FROM location_attribute WHERE attribute_type_id = 5) AS facility_code ON district_code.location_id = facility_code.location_id WHERE facility_code.location_id = :id")
    LiveData<FacilityDistrictCode> getFacilitydistrictCodeByLocationId(long id);
}
