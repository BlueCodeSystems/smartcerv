package zm.gov.moh.core.repository.database.entity.derived;

import androidx.room.*;

import com.squareup.moshi.Json;

import org.threeten.bp.LocalDateTime;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class Client {

    @ColumnInfo(name = "identifier_type")
    @Json(name = "identifier_type")
    private int identifierType;

    @ColumnInfo(name = "patient_id")
    @Json(name = "patient_id")
    private long patientId;

    @ColumnInfo(name = "identifier")
    @Json(name = "identifier")
    private String identifier;

    @ColumnInfo(name = "given_name")
    @Json(name = "given_name")
    private String givenName;

    @ColumnInfo(name = "family_name")
    @Json(name = "family_name")
    private String familyName;

    @ColumnInfo(name = "middle_name")
    @Json(name = "middle_name")
    private String middle_name;

    @ColumnInfo(name = "gender")
    @Json(name = "gender")
    private String gender;

    @ColumnInfo(name = "birthdate")
    @Json(name = "birthdate")
    private LocalDateTime birthDate;

    public enum Type {
        INPATIENT, OUTPATIENT, PARTITION
    }


    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public int getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(int identifierType) {
        this.identifierType = identifierType;
    }
}