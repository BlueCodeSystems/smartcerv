package zm.gov.moh.core.repository.database.dao.derived;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import zm.gov.moh.core.repository.database.entity.derived.StringIntEntry;

@Dao
public interface MetricsDao {

    @Query("select date(date_created) as string, count(*) as integer from patient group by date(date_created)")
    List<StringIntEntry> countRegisteredClientsLastDa();

    @Query("select count(distinct patient_id) as count from patient join obs on patient.patient_id = obs.person_id where obs.location_id = :locationId and obs.concept_id = :conceptId")
    Long countClientsByObs(long locationId, long conceptId);

    @Query("select count(distinct patient_id) as count from patient join obs on patient.patient_id = obs.person_id where obs.location_id = :locationId and obs.value_coded = :codedValueConceptId")
    LiveData<Long> countClientsByObsCodedValue(long locationId, long codedValueConceptId);

    @Query("select count(distinct patient_id) as count from patient join obs on patient.patient_id = obs.person_id where obs.location_id = :locationId and obs.value_coded in (:codedValueConceptIds)")
    LiveData<Long> countClientsByObsCodedValue(long locationId, long... codedValueConceptIds);

    @Query("select count(distinct patient.patient_id) as count from patient join patient_identifier on patient.patient_id = patient_identifier.patient_id where patient_identifier.location_id = :locationId and patient_identifier.identifier_type in (:patientIdentifierType)")
    Long countClientsByLocationPatientIdType(long locationId, long... patientIdentifierType);
}