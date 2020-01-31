package zm.gov.moh.core.repository.database.dao.domain;

import org.threeten.bp.LocalDateTime;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;

@Dao
public interface PersonAddressDao extends Synchronizable<PersonAddress> {

    //gets all persons
    @Query("SELECT * FROM person_address WHERE preferred = 1")
    List<PersonAddress> getAll();

    @Query("SELECT person_address_id FROM person_address")
    List<Long> getIds();

    @Query("SELECT MAX(datetime) AS datetime FROM (SELECT CASE WHEN COALESCE(date_created,'1970-01-01T00:00:00') >= COALESCE(date_changed,'1970-01-01T00:00:00') THEN date_created ELSE date_changed END datetime FROM person_address WHERE person_id IN (SELECT DISTINCT patient_id FROM patient_identifier WHERE location_id = :locationId))")
    LocalDateTime getMaxDatetime(long locationId);

    // Inserts single getPersons
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonAddress personAddress);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonAddress... personAddresses);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<PersonAddress> personAddresses);


    //get persons address by getPersons id
    @Query("SELECT * FROM person_address WHERE person_id = :id AND preferred = 1")
    LiveData<PersonAddress> findByPersonIdObservable(long id);


    @Query("SELECT * FROM person_address WHERE person_id = :id AND preferred = 1")
    PersonAddress findByPersonId(long id);

    @Query("SELECT * FROM person_address WHERE person_id = :id AND preferred = 1 AND date_changed >= :lastModifiedDate")
    PersonAddress findByPersonId(long id, LocalDateTime lastModifiedDate);

    @Query("UPDATE person_address SET person_id = :remote WHERE person_id = :local")
    void replacePerson(long local, long remote);

    @Query("SELECT MAX(person_address_id) FROM person_address")
    Long getMaxId();

    @Override
    @Query("SELECT * FROM (SELECT * FROM person_address WHERE person_address_id NOT IN (:id)) WHERE person_address_id >= :offsetId")
    PersonAddress[] findEntityNotWithId(long offsetId,long... id);
}
