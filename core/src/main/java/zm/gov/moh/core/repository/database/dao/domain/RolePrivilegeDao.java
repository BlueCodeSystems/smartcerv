package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.RolePrivilege;

@Dao
public interface RolePrivilegeDao {

    @Query("SELECT * FROM role_privilege")
    RolePrivilege getAllRolePrivilege();

    @Insert
    void insert(RolePrivilege rolePrivilege);
}
