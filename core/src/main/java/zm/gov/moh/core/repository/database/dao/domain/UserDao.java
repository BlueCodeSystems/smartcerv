package zm.gov.moh.core.repository.database.dao.domain;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.Provider;
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
