package zm.gov.moh.core.repository.database.dao.domain;

import org.threeten.bp.LocalDateTime;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;
import java.util.Set;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;

@Dao
public interface PersonNameDao extends Synchronizable<PersonName> {

    @Query("SELECT person_name_id FROM person_name")
    List<Long> getIds();

    @Query("SELECT MAX(datetime) AS datetime FROM (SELECT CASE WHEN COALESCE(date_created,'1970-01-01T00:00:00') >= COALESCE(date_changed,'1970-01-01T00:00:00') THEN date_created ELSE date_changed END datetime FROM person_name WHERE person_id IN (SELECT DISTINCT patient_id FROM patient_identifier WHERE location_id = :locationId) AND uuid IS NOT NULL)")
    LocalDateTime getMaxDatetime(long locationId);

    @Query("SELECT person_name.* FROM person_name JOIN provider ON person_name.person_id = provider.person_id WHERE provider_id = :providerId AND person_name.voided = 0")
    PersonName getByInsightProviderId(long providerId);

    @Query("SELECT person_name.* FROM person_name JOIN provider ON person_name.person_id = provider.person_id WHERE provider_id = :providerId AND person_name.voided = 0")
    LiveData<PersonName> getByProviderId(long providerId);

    //gets all persons attribute type
    @Query("SELECT * FROM person_name")
    List<PersonName> getAll();

    // Inserts single getPersons name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonName personName);

    // Inserts single getPersons name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonName... personNames);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<PersonName> personNames);

    //get persons name by getPersons id
    @Query("SELECT * FROM person_name WHERE person_id = :id AND voided = 0")
    PersonName findByPersonId(long id);

    @Query("SELECT * FROM person_name WHERE person_id = :id AND date_changed NOT NULL AND date_changed >= :lastModified AND voided = 0")
    PersonName findByPersonId(long id, LocalDateTime lastModified);

    @Query("SELECT * FROM person_name WHERE person_id IN (:ids)")
    List<PersonName> findByPersonId(Set<Long> ids);

    @Query("UPDATE person_name SET person_id = :remote WHERE person_id = :local")
    void replacePerson(long local, long remote);

    //get persons name by getPersons id
    @Override
    @Query("SELECT * FROM (SELECT * FROM person_name WHERE person_name_id NOT IN (:id)) WHERE person_name_id >= :offsetId")
    PersonName[] findEntityNotWithId(long offsetId,long... id);

    @Query("SELECT MAX(person_name_id) FROM person_name")
    Long getMaxId();
}