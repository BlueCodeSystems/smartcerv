package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;
import zm.gov.moh.core.repository.database.entity.SynchronizableEntity;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

@Entity(tableName = "person_name")
public class PersonName extends SynchronizableEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "person_name_id")
    @Json(name = "person_name_id")
    private long personNameId;

    @ColumnInfo(name = "preferred")
    @Json(name = "preferred")
    private short preferred;

    @ColumnInfo(name = "person_id")
    @Json(name = "person_id")
    private long personId;

    @ColumnInfo(name = "prefix")
    @Json(name = "prefix")
    private String prefix;

    @ColumnInfo(name = "given_name")
    @Json(name = "given_name")
    private String givenName;

    @ColumnInfo(name = "middle_name")
    @Json(name = "middle_name")
    private String middleName;

    @ColumnInfo(name = "family_name_prefix")
    @Json(name = "family_name_prefix")
    private String familyNamePrefix;

    @ColumnInfo(name = "family_name")
    @Json(name = "family_name")
    private String familyName;

    @ColumnInfo(name = "family_name2")
    @Json(name = "family_name2")
    private String familyName2;

    @ColumnInfo(name = "family_name_suffix")
    @Json(name = "family_name_suffix")
    private String familyNameSuffix;

    @ColumnInfo(name = "degree")
    @Json(name = "degree")
    private String degree;

    @ColumnInfo(name = "creator")
    @Json(name = "creator")
    private long creator;

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

    @ColumnInfo(name = "changed_by")
    @Json(name = "changed_by")
    private Long changedBy;

    @ColumnInfo(name = "date_changed")
    @Json(name = "date_changed")
    private LocalDateTime dateChanged;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    public PersonName(long personId, String givenName, String familyName, short preferred){

        this.personId = personId;
        this.givenName = givenName;
        this.familyName = familyName;
        this.preferred = preferred;

    }

    @Ignore
    public PersonName(long personNameId, long personId, String givenName, String familyName, short preferred,LocalDateTime dateCreated){

        this.personNameId = personNameId;
        this.personId = personId;
        this.givenName = givenName;
        this.familyName = familyName;
        this.preferred = preferred;
        this.dateCreated = dateCreated;

    }

    public long getPersonNameId() {
        return personNameId;
    }

    public void setPersonNameId(long personNameId) {
        this.personNameId = personNameId;
    }

    public short getPreferred() {
        return preferred;
    }

    public void setPreferred(short preferred) {
        this.preferred = preferred;
    }

    @Override
    public long getId() {
        return personNameId;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFamilyNamePrefix() {
        return familyNamePrefix;
    }

    public void setFamilyNamePrefix(String familyNamePrefix) {
        this.familyNamePrefix = familyNamePrefix;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilyName2() {
        return familyName2;
    }

    public void setFamilyName2(String familyName2) {
        this.familyName2 = familyName2;
    }

    public String getFamilyNameSuffix() {
        return familyNameSuffix;
    }

    public void setFamilyNameSuffix(String familyNameSuffix) {
        this.familyNameSuffix = familyNameSuffix;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
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