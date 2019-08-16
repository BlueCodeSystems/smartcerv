package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.RoleRole;

@Dao
public interface RoleRoleDao {

    @Query("SELECT * FROM role_role")
    RoleRole getAllRoleRole();

    @Insert
    void insert(RoleRole roleRole);
}
