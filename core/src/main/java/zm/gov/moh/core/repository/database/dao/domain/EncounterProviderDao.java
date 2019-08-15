package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.EncounterProvider;

@Dao
public interface EncounterProviderDao {

    @Query("SELECT MAX(encounter_provider_id) FROM encounter_provider")
    Long getMaxId();

    //get by patient and encouter type
    @Query("SELECT * FROM encounter_provider WHERE encounter_id = :encounterId AND provider_id = :providerId")
    LiveData<EncounterProvider> getByEncounterProviderId(long providerId, long encounterId);

    //gets all locations
    @Query("SELECT * FROM encounter_provider WHERE provider_id = :id")
    LiveData<EncounterProvider> getByProviderId(long id);

    @Query("SELECT * FROM encounter_provider WHERE encounter_id = :id ORDER BY date_created DESC LIMIT 1")
    EncounterProvider getByEncounterId(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EncounterProvider... encounterProviders);
}