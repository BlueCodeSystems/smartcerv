package zm.gov.moh.core.repository.database.entity.domain;

import androidx.room.*;

import com.squareup.moshi.Json;

@Entity(tableName = "role_role")
public class RoleRole {

    @ColumnInfo(name = "parent_role")
    @Json(name = "parent_role")
    private String parentRole;

    @ColumnInfo(name = "child_role")
    @Json(name = "child_role")
    private String childRole;

    public String getParentRole() {
        return parentRole;
    }

    public void setParentRole(String parentRole) {
        this.parentRole = parentRole;
    }

    public String getChildRole() {
        return childRole;
    }

    public void setChildRole(String childRole) {
        this.childRole = childRole;
    }
}
