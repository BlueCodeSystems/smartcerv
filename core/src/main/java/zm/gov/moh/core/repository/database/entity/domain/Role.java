package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

@Entity(tableName = "role")
public class Role {

    @PrimaryKey
    @ColumnInfo(name = "role")
    @Json(name = "role")
    private String role;

    @ColumnInfo(name = "describe")
    @Json(name = "describe")
    private String describe;

    @ColumnInfo(name = "uuid")
    @Json(name = "uuid")
    private String uuid;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
