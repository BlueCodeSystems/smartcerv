package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;
import zm.gov.moh.core.repository.database.entity.SynchronizableEntity;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalDateTime;

@Entity(tableName = "person_address")
public class PersonAddress extends SynchronizableEntity {

    @PrimaryKey(autoGenerate = true)
    public long person_address_id;
    public long person_id;
    public short preferred;
    public String address1;
    public String address2;
    public String address3;
    public String address4;
    public String address5;
    public String address6;
    public String address7;
    public String address8;
    public String address9;
    public String address10;
    public String address11;
    public String address12;
    public String address13;
    public String address14;
    public String address15;
    public String city_village;
    public String state_province;
    public String postal_code;
    public String country;
    public String latitude;
    public String longitude;
    public LocalDateTime start_date;
    public LocalDateTime end_date;
    public Long creator;
    public LocalDateTime date_created;
    public short voided;
    public Long voided_by;
    public LocalDateTime date_voided;
    public String void_reason;
    public String country_district;
    public LocalDateTime date_changed;
    public Long changed_by;
    public String uuid;

    public PersonAddress(long person_id, String address1, String city_village, String state_province, short preferred ){

        this.person_id = person_id;
        this.address1 = address1;
        this.city_village = city_village;
        this.state_province = state_province;
        this.preferred = preferred;
    }

    @Ignore
    public PersonAddress(long person_address_id,long person_id, String address1, String city_village, String state_province, short preferred ){

        this.person_address_id = person_address_id;
        this.person_id = person_id;
        this.address1 = address1;
        this.city_village = city_village;
        this.state_province = state_province;
        this.preferred = preferred;
    }

    @Override
    public long getId() {
        return person_address_id;
    }
}
