package zm.gov.moh.core.repository.database.dao.domain;


import org.threeten.bp.LocalDateTime;

import androidx.lifecycle.LiveData;

import java.util.List;

import androidx.room.*;
import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;

@Dao
public interface PersonDao extends Synchronizable<Person> {

    //gets all persons
    @Query("SELECT * FROM person")
    LiveData<List<Person>> getAll();

    @Query("SELECT MAX(datetime) AS datetime FROM (SELECT CASE WHEN COALESCE(date_created,'1970-01-01T00:00:00') >= COALESCE(date_changed,'1970-01-01T00:00:00') THEN date_created ELSE date_changed END datetime FROM person WHERE person_id IN (SELECT DISTINCT patient_id FROM patient_identifier WHERE location_id = :locationId) AND uuid IS NOT NULL)")
    LocalDateTime getMaxDatetime(long locationId);

    @Query("SELECT * FROM person")
    List<Person> getAllT();

    @Query("SELECT person_id FROM person")
    List<Long> getIds();

    // Inserts single getPersons
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Person person);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Person> person);

    // Inserts single getPersons
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Person... persons);

    //get getPersons by id
    @Query("SELECT * FROM person WHERE person_id = :id")
    Person findById(long id);

    @Query("SELECT uuid FROM person WHERE person_id = :patientId")
    String findByPatientId(long patientId);

    @Query("UPDATE person SET person_id = :remote WHERE person_id = :local ")
    void replacePerson(long local, long remote );

    @Query("SELECT MAX(person_id) FROM person")
    Long getMaxId();

    @Override
    @Query("SELECT * FROM (SELECT * FROM person WHERE person_id NOT IN (:id)) WHERE person_id >= :offsetId")
    Person[] findEntityNotWithId(long offsetId,long[] id);

    //get persons phone number
    @Query("SELECT value from person_attribute WHERE person_id=:personID AND person_attribute_type_id=8 ")
    String getPersonPhoneumberById(Long personID);

    //get person nrc number

    @Query("SELECT value from person_attribute WHERE person_id=:personID AND person_attribute_type_id=12")
    String getPersonNRCNumberbyId(Long personID);
    //update person nrc number
    @Query("Update person_attribute SET value=:newNRCNumber,date_changed=:lastDateModified WHERE person_id=:personID AND person_attribute_type_id=8")
    void updateNRCNumberBydID(Long personID, String newNRCNumber, LocalDateTime lastDateModified);
}