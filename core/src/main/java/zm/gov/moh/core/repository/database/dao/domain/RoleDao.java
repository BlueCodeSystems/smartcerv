package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.Role;

@Dao
public interface RoleDao {

    @Query("SELECT * FROM role")
    Role getAllRole(Role role);

    @Insert
    void insert(Role role);
}
