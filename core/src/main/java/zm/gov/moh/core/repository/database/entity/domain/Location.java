package zm.gov.moh.core.repository.database.entity.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZonedDateTime;

@Entity
public class Location {

    @PrimaryKey
    public long location_id;
    public String name;
    public String description;
    public String address1;
    public String address2;
    public String city_village;
    public String state_province;
    public String postal_code;
    public String country;
    public String latitude;
    public String longitude;
    public long creator;
    public ZonedDateTime date_created;
    public String county_district;
    public String address3;
    public String address4;
    public String address5;
    public String address6;
    public short retired;
    public Long retired_by;
    public ZonedDateTime date_retired;
    public String retire_reason;
    public Long parent_location;
    public String uuid;
    public Long changed_by;
    public ZonedDateTime date_changed;
    public String address7;
    public String address8;
    public String address9;
    public String address10;
    public String address11;
    public String address12;
    public String address13;
    public String address14;
    public String address15;
}
