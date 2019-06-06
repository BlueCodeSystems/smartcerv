package zm.gov.moh.core.repository.database.entity.domain;


import androidx.room.*;
import zm.gov.moh.core.repository.database.entity.SynchronizableEntity;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

@Entity
public class Person extends SynchronizableEntity {

    @PrimaryKey
    @ColumnInfo(name = "person_id")
    @Json(name = "person_id")
    public long personId;

    @ColumnInfo(name = "gender")
    @Json(name = "gender")
    private String gender;

    @ColumnInfo(name = "birthdate")
    @Json(name = "birthdate")
    private LocalDateTime birthDate;

    @ColumnInfo(name = "birthdate_estimated")
    @Json(name = "birthdate_estimated")
    private short birthdateEstimated;

    @ColumnInfo(name = "dead")
    @Json(name = "dead")
    private short dead;

    @ColumnInfo(name = "death_date")
    @Json(name = "death_date")
    private LocalDate deathDate;

    @ColumnInfo(name = "cause_of_death")
    @Json(name = "cause_of_death")
    private Long causeOfDeath;

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

    @ColumnInfo(name = "voided_by")
    @Json(name = "voided_by")
    private Long voidedBy;

    @ColumnInfo(name = "date_voided")
    @Json(name = "date_voided")
    private LocalDateTime dateVoided;

    @ColumnInfo(name = "void_reason")
    @Json(name = "void_reason")
    private String voidReason;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    @ColumnInfo(name = "deathdate_estimated")
    @Json(name = "deathdate_estimated")
    private short deathdateEstimated;

    @ColumnInfo(name = "birthtime")
    @Json(name = "birthtime")
    private LocalDateTime birthTime;

    public Person(long personId, LocalDateTime birthDate,String gender, LocalDateTime dateCreated){

        this.personId = personId;
        this.birthDate = birthDate;
        this.gender = gender;
        this.dateCreated = dateCreated;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public long getId() {
        return personId;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public short getBirthdateEstimated() {
        return birthdateEstimated;
    }

    public void setBirthdateEstimated(short birthdateEstimated) {
        this.birthdateEstimated = birthdateEstimated;
    }

    public short getDead() {
        return dead;
    }

    public void setDead(short dead) {
        this.dead = dead;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

    public Long getCauseOfDeath() {
        return causeOfDeath;
    }

    public void setCauseOfDeath(Long causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public short getDeathdateEstimated() {
        return deathdateEstimated;
    }

    public void setDeathdateEstimated(short deathdateEstimated) {
        this.deathdateEstimated = deathdateEstimated;
    }

    public LocalDateTime getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(LocalDateTime birthTime) {
        this.birthTime = birthTime;
    }
}