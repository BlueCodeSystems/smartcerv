package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.UserRole;

@Dao
public interface UserRoleDao {

    @Query("SELECT * FROM user_role")
    UserRole getAllUserRole();

    @Insert
    void insert(UserRole userRole);
}
