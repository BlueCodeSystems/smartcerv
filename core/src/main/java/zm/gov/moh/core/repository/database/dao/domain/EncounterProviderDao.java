package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.EncounterProvider;

@Dao
public interface EncounterProviderDao {

    //get by patient and encouter type
    @Query("SELECT * FROM encounter_provider WHERE encounter_id = :encounterId AND provider_id = :providerId")
    LiveData<EncounterProvider> getByEncounterProviderId(long providerId, long encounterId);

    //gets all locations
    @Query("SELECT * FROM encounter_provider WHERE provider_id = :id")
    LiveData<EncounterProvider> getByProviderId(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EncounterProvider... encounterProviders);
}