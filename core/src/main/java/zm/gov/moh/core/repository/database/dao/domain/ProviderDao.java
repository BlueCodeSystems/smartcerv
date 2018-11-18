package zm.gov.moh.core.repository.database.dao.domain;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.repository.database.entity.domain.Provider;

@Dao
public interface ProviderDao {

    //gets all locations
    @Query("SELECT * FROM provider ORDER BY name ASC")
    LiveData<List<Provider>> getAll();

    //gets all locations
    @Query("SELECT * FROM provider WHERE person_id = :id")
    LiveData<Provider> getByPersonId(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Provider... providers);
}