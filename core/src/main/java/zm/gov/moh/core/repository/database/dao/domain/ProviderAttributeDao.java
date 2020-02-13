package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.ProviderAttribute;

@Dao
public interface ProviderAttributeDao {

    @Query("SELECT * FROM provider_attribute")
    List<ProviderAttribute> getAll();

    @Query("SELECT * FROM provider_attribute WHERE attribute_type_id = (SELECT provider_attribute_type_id FROM provider_attribute_type WHERE uuid = :attributeTypeUuid) AND provider_id = :providerId AND voided = 0")
    LiveData<ProviderAttribute> getAttributeByType(String attributeTypeUuid, long providerId);

    @Query("SELECT * FROM provider_attribute WHERE attribute_type_id = (SELECT provider_attribute_type_id FROM provider_attribute_type WHERE uuid = :attributeTypeUuid) AND provider_id = :providerId AND voided = 0")
    ProviderAttribute[] getAttributeByTypeBlocking(String attributeTypeUuid, long providerId);

    @Query("SELECT * FROM provider_attribute WHERE provider_id = :id AND attribute_type_id = 1")
    ProviderAttribute findByProviderId(long id);    // attribute_type_id = 1, for phone_number

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProviderAttribute... attributes);
}
