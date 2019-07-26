package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.User;

@Dao
public interface UserDao {

    //gets all locations
    @Query("SELECT * FROM user ORDER BY username ASC")
    List<User> getAll();

    //gets all locations
    @Query("SELECT * FROM user WHERE uuid = :uuid")
    LiveData<User> getByUuid(String uuid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... users);
}
