package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.UserProperty;

@Dao
public interface UserPropertyDao {

    @Query("SELECT * FROM user_property")
    UserProperty getAllUserProperty();

    @Insert
    void insert(UserProperty userProperty);
}
