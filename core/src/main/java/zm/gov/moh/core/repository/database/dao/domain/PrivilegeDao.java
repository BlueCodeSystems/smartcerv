package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.Privilege;

@Dao
public interface PrivilegeDao {

    @Query("SELECT * FROM privilege")
    Privilege getAllPrivilege();

    @Insert
    void insert(Privilege privilege);
}
