package zm.gov.moh.core.repository.database.dao.derived;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.Obs;
@Dao
public interface VitalsDao {

    @Query("SELECT * FROM obs WHERE concept_id = 5090 AND person_id = :id AND obs_datetime = (SELECT MAX(obs_datetime) FROM obs WHERE  concept_id = 5090 AND person_id = :id)")
    LiveData<Obs> getHeightByPersonId(long id);

    @Query("SELECT * FROM obs WHERE  concept_id = 5089 AND person_id = :id AND obs_datetime = (SELECT MAX(obs_datetime) FROM obs WHERE  concept_id = 5089 AND person_id = :id)")
    LiveData<Obs> getWeightByPersonId(long id);

    @Query("SELECT * FROM obs WHERE  concept_id = 5088 AND person_id = :id AND obs_datetime = (SELECT MAX(obs_datetime) FROM obs WHERE  concept_id = 5088 AND person_id = :id)")
    LiveData<Obs> getTemperatureByPersonId(long id);

    @Query("SELECT * FROM obs WHERE  concept_id = 5087 AND person_id = :id AND obs_datetime = (SELECT MAX(obs_datetime) FROM obs WHERE  concept_id = 5087 AND person_id = :id)")
    LiveData<Obs> getPulseByPersonId(long id);

    @Query("SELECT * FROM obs WHERE  concept_id = 5242 AND person_id = :id AND obs_datetime = (SELECT MAX(obs_datetime) FROM obs WHERE  concept_id = 5242 AND person_id = :id)")
    LiveData<Obs> getRespiratoryRateByPersonId(long id);

    @Query("SELECT * FROM obs WHERE  concept_id = 5085 AND person_id = :id AND obs_datetime = (SELECT MAX(obs_datetime) FROM obs WHERE  concept_id = 5085 AND person_id = :id)")
    LiveData<Obs> getSysbolicBloodPressureByPersonId(long id);

    @Query("SELECT * FROM obs WHERE  concept_id = 5086 AND person_id = :id AND obs_datetime = (SELECT MAX(obs_datetime) FROM obs WHERE  concept_id = 5086 AND person_id = :id)")
    LiveData<Obs> getDiabolicBloodPressureByPersonId(long id);

    @Query("SELECT * FROM obs WHERE  concept_id = 5092 AND person_id = :id AND obs_datetime = (SELECT MAX(obs_datetime) FROM obs WHERE  concept_id = 5092 AND person_id = :id)")
    LiveData<Obs> getBloodOxygenByPersonId(long id);
}
