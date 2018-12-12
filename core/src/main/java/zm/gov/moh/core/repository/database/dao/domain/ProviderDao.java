package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

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