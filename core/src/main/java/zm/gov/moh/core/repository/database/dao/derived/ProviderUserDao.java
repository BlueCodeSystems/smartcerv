package zm.gov.moh.core.repository.database.dao.derived;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.derived.ProviderUser;

@Dao
public interface ProviderUserDao {

    //@Query("SELECT provider_id ,provider.person_id, user_id, given_name, family_name, username FROM provider JOIN person_name ON provider.person_id = person_name.person_id JOIN user ON user.person_id = provider.person_id WHERE provider.person_id = :uuid")
   // ProviderUser getByUserUuid(long uuid);

    @Query("SELECT provider_id,provider.person_id, user_id, identifier, person_name.given_name,person_name.family_name, username FROM provider JOIN user ON provider.identifier = user.system_id JOIN person_name ON person_name.person_id = provider.person_id")
    List<ProviderUser> getAllByUserUuid();
}
