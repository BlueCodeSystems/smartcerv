package zm.gov.moh.core.repository.database.entity.derived;

import androidx.room.*;

import com.squareup.moshi.Json;

@Entity
public class ProviderUser {

    @ColumnInfo(name = "provider_id")
    @Json(name = "provider_id")
    private Long providerId;

    @ColumnInfo(name = "person_id")
    @Json(name = "person_id")
    private Long personId;

    @ColumnInfo(name = "user_id")
    @Json(name = "user_id")
    private Long userId;

    @ColumnInfo(name = "identifier")
    @Json(name = "identifier")
    private String identifier;

    @ColumnInfo(name = "given_name")
    @Json(name = "given_name")
    private String givenName;

    @ColumnInfo(name = "family_name")
    @Json(name = "family_name")
    private String familyName;

    @ColumnInfo(name = "username")
    @Json(name = "username")
    private String userName;

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
