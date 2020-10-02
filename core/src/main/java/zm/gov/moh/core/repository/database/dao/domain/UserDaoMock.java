package zm.gov.moh.core.repository.database.dao.domain;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import zm.gov.moh.core.model.UserMock;
import zm.gov.moh.core.repository.database.entity.domain.User;

@Dao
public interface UserDaoMock {

    //gets all locations
    @Query("SELECT * FROM user ORDER BY username ASC")
    List<UserMock> getAll();

    //gets all locations
    @Query("SELECT * FROM user WHERE uuid = :uuid")
    LiveData<UserMock> getByUuid(String uuid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(zm.gov.moh.core.model.UserMock users);

    @Query("SELECT * FROM user ORDER BY username ASC")
    List<zm.gov.moh.core.model.UserMock> findUsersByName(String george);
}
