package zm.gov.moh.core.repository.database.dao.derived;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.derived.ProviderUserNumber;

@Dao
public interface ProviderUserNumberDao {

    @Query("SELECT provider.provider_id, provider.person_id, user_id, provider.identifier, " +
        "person_name.given_name, person_name.family_name, user.username, " +
        "provider_attribute.value_reference phone_number FROM provider JOIN user ON " +
        "provider.identifier = user.system_id JOIN person_name ON " +
        "person_name.person_id = provider.person_id LEFT JOIN provider_attribute ON " +
        "provider_attribute.provider_id = provider.provider_id " +
        "WHERE user.uuid= :uuid")
    LiveData<ProviderUserNumber> getAllNumbersByUser(String uuid);
}
