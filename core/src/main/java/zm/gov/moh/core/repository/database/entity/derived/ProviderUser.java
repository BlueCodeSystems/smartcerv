package zm.gov.moh.core.repository.database.entity.derived;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;

@Entity
public class ProviderUser {

    public Long provider_id;
    public Long person_id;
    public Long user_id;
    public String identifier;
    public String given_name;
    public String family_name;
    public String username;
}
