package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

@Entity(tableName = "role_privilege")
public class RolePrivilege {

    @ColumnInfo(name = "role")
    @Json(name = "role")
    private String role;

    @ColumnInfo(name = "privilege")
    @Json(name = "privilege")
    private String privilege;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}
