package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;
import zm.gov.moh.core.repository.database.entity.SynchronizableEntity;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalDateTime;

@Entity(tableName = "person_address")
public class PersonAddress extends SynchronizableEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "person_address_id")
    @Json(name = "person_address_id")
    private long personAddressId;

    @ColumnInfo(name = "person_id")
    @Json(name = "person_id")
    private long personId;

    @ColumnInfo(name = "preferred")
    @Json(name = "preferred")
    private short preferred;

    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String address5;
    private String address6;
    private String address7;
    private String address8;
    private String address9;
    private String address10;
    private String address11;
    private String address12;
    private String address13;
    private String address14;
    private String address15;

    @ColumnInfo(name = "city_village")
    @Json(name = "city_village")
    private String cityVillage;

    @ColumnInfo(name = "state_province")
    @Json(name = "state_province")
    private String stateProvince;

    @ColumnInfo(name = "postal_code")
    @Json(name = "postal_code")
    private String postalCode;

    @ColumnInfo(name = "country")
    @Json(name = "country")
    private String country;

    @ColumnInfo(name = "latitude")
    @Json(name = "latitude")
    private String latitude;

    @ColumnInfo(name = "longitude")
    @Json(name = "longitude")
    private String longitude;

    @ColumnInfo(name = "start_date")
    @Json(name = "start_date")
    private LocalDateTime startDate;

    @ColumnInfo(name = "end_date")
    @Json(name = "end_date")
    private LocalDateTime endDate;

    @ColumnInfo(name = "creator")
    @Json(name = "creator")
    private Long creator;

    @ColumnInfo(name = "date_created")
    @Json(name = "date_created")
    private LocalDateTime dateCreated;

    @ColumnInfo(name = "voided")
    @Json(name = "voided")
    private short voided;

    @ColumnInfo(name = "voided_by")
    @Json(name = "voided_by")
    private Long voidedBy;

    @ColumnInfo(name = "date_voided")
    @Json(name = "date_voided")
    private LocalDateTime dateVoided;

    @ColumnInfo(name = "void_reason")
    @Json(name = "void_reason")
    private String voidReason;

    @ColumnInfo(name = "country_district")
    @Json(name = "country_district")
    private String countryDistrict;

    @ColumnInfo(name = "date_changed")
    @Json(name = "date_changed")
    private LocalDateTime dateChanged;

    @ColumnInfo(name = "changed_by")
    @Json(name = "changed_by")
    private Long changedBy;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    public PersonAddress(long personId, String address1, String cityVillage, String stateProvince, short preferred ){

        this.personId = personId;
        this.address1 = address1;
        this.cityVillage = cityVillage;
        this.stateProvince = stateProvince;
        this.preferred = preferred;
    }

    @Ignore
    public PersonAddress(long personAddressId,long personId, String address1, String cityVillage, String stateProvince, short preferred, LocalDateTime dateCreated){

        this.personAddressId = personAddressId;
        this.personId = personId;
        this.address1 = address1;
        this.cityVillage = cityVillage;
        this.stateProvince = stateProvince;
        this.preferred = preferred;
        this.dateCreated = dateCreated;
    }

    @Override
    public long getId() {
        return personId;
    }

    public long getPersonAddressId() {
        return personAddressId;
    }

    public void setPersonAddressId(long personAddressId) {
        this.personAddressId = personAddressId;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public short getPreferred() {
        return preferred;
    }

    public void setPreferred(short preferred) {
        this.preferred = preferred;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getAddress5() {
        return address5;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
    }

    public String getAddress6() {
        return address6;
    }

    public void setAddress6(String address6) {
        this.address6 = address6;
    }

    public String getAddress7() {
        return address7;
    }

    public void setAddress7(String address7) {
        this.address7 = address7;
    }

    public String getAddress8() {
        return address8;
    }

    public void setAddress8(String address8) {
        this.address8 = address8;
    }

    public String getAddress9() {
        return address9;
    }

    public void setAddress9(String address9) {
        this.address9 = address9;
    }

    public String getAddress10() {
        return address10;
    }

    public void setAddress10(String address10) {
        this.address10 = address10;
    }

    public String getAddress11() {
        return address11;
    }

    public void setAddress11(String address11) {
        this.address11 = address11;
    }

    public String getAddress12() {
        return address12;
    }

    public void setAddress12(String address12) {
        this.address12 = address12;
    }

    public String getAddress13() {
        return address13;
    }

    public void setAddress13(String address13) {
        this.address13 = address13;
    }

    public String getAddress14() {
        return address14;
    }

    public void setAddress14(String address14) {
        this.address14 = address14;
    }

    public String getAddress15() {
        return address15;
    }

    public void setAddress15(String address15) {
        this.address15 = address15;
    }

    public String getCityVillage() {
        return cityVillage;
    }

    public void setCityVillage(String cityVillage) {
        this.cityVillage = cityVillage;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
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

    public short getVoided() {
        return voided;
    }

    public void setVoided(short voided) {
        this.voided = voided;
    }

    public Long getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Long voidedBy) {
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

    public String getCountryDistrict() {
        return countryDistrict;
    }

    public void setCountryDistrict(String countryDistrict) {
        this.countryDistrict = countryDistrict;
    }

    public LocalDateTime getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(LocalDateTime dateChanged) {
        this.dateChanged = dateChanged;
    }

    public Long getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Long changedBy) {
        this.changedBy = changedBy;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
