package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.ProviderAttribute;

@Dao
public interface ProviderAttributeDao {

    @Query("SELECT * FROM provider_attribute")
    List<ProviderAttribute> getAll();

    @Query("SELECT * FROM provider_attribute WHERE provider_id = :id")
    ProviderAttribute findByProviderId(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProviderAttribute... attributes);
}
