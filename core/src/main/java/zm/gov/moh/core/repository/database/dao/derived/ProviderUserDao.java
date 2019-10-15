package zm.gov.moh.core.repository.database.dao.derived;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.derived.DrawerProvider;
import zm.gov.moh.core.repository.database.entity.derived.ProviderUser;

@Dao
public interface ProviderUserDao {

    @Query("SELECT provider_id,provider.person_id, user_id, identifier, person_name.given_name,person_name.family_name, username FROM provider JOIN user ON provider.identifier = user.system_id JOIN person_name ON person_name.person_id = provider.person_id WHERE user.uuid = :uuid")
    LiveData<ProviderUser> getAllByUserUuid(String uuid);

    @Query("SELECT provider_id,provider.person_id, user_id, identifier, person_name.given_name,person_name.family_name, username FROM provider JOIN user ON provider.identifier = user.system_id JOIN person_name ON person_name.person_id = provider.person_id WHERE user.uuid = :uuid")
    ProviderUser getByUserUuid(String uuid);

   //return username for the drawer
    @Query("SELECT username,given_name, family_name from user join person_name on user.person_id=person_name.person_id join provider on person_name.person_id=provider.person_id WHERE provider.provider_id=:id")
    LiveData<DrawerProvider>getDrawerUser(long id);


}
