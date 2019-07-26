package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.ProviderAttributeType;

@Dao
public interface ProviderAttributeTypeDao {

    @Query("SELECT * FROM provider_attribute_type")
    List<ProviderAttributeType> getAll();

    @Query("SELECT * FROM provider_attribute_type WHERE provider_attribute_type_id = :id")
    ProviderAttributeType findById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProviderAttributeType... providerAttributeType);
}
