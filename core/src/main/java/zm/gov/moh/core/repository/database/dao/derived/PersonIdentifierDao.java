package zm.gov.moh.core.repository.database.dao.derived;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import zm.gov.moh.core.repository.database.entity.derived.PersonIdentifier;
import zm.gov.moh.core.repository.database.entity.derived.ProviderUser;
import zm.gov.moh.core.repository.database.entity.domain.ConceptAnswer;

@Dao
public interface PersonIdentifierDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonIdentifier... personIdentifiers);

    @Query("SELECT uuid FROM person_identifier WHERE person_id = :personId")
    String getUuidByPersonId(long personId);

    @Query("SELECT * FROM person_identifier")
    List<PersonIdentifier> getByPersonId();
}
