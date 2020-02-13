package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

@Entity(tableName = "user_role")
public class UserRole {

    @PrimaryKey
    @ColumnInfo(name = "user_id") //name here should same as in Json
    @Json(name = "user_id")
    private long userId;

    @ColumnInfo(name = "role")
    @Json(name = "role")
    private String role;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
