package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.repository.database.entity.domain.PatientEntity;

@Dao
public interface PatientDao extends Synchronizable<Long> {

    //gets all persons attribute type
    @Query("SELECT * FROM patient")
    List<PatientEntity> getAll();

    //gets all persons attribute type
    @Query("SELECT patient_id FROM patient")
    List<Long> getIds();

    //gets all persons attribute type
    @Query("SELECT patient_id FROM patient")
    Long[] getAllP();

    // Inserts single getPersons name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PatientEntity patient);

    // Inserts single getPersons name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PatientEntity... patients);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<PatientEntity> patients);

    //get persons name by getPersons id
    @Query("SELECT * FROM patient WHERE patient_id = :id")
    PatientEntity findById(long id);

    @Query("UPDATE patient SET patient_id = :remote  WHERE patient_id = :local")
    void replacePatient(long local, long remote);

    @Query("SELECT patient_id FROM patient")
    List <Long> findByAllPatientId();

    //get persons name by getPersons id
    @Query("SELECT MAX(patient_id) FROM patient")
    Long getMaxId();


    //get total number of patients seen
    @Query("SELECT COUNT(DISTINCT patient.patient_id) from patient JOIN patient_identifier ON patient.patient_id=patient_identifier.patient_id JOIN visit ON patient_identifier.patient_id=visit.patient_id WHERE patient_identifier.identifier_type=4 AND patient.voided=0 AND visit.voided=0 AND patient_identifier.location_id =:location_id ORDER BY patient.patient_id DESC")
    LiveData <Long> getTotalSeenClients(long location_id);

    //get total number of patients registered
        @Query("SELECT COUNT(DISTINCT patient.patient_id) from patient JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id  WHERE identifier_type=4 AND patient.voided=0 AND patient_identifier.location_id=:location_id ORDER BY patient.patient_id DESC")
    LiveData <Long> getTotalRegistered(long location_id);

        //get total number of patients screened
    @Query("SELECT COUNT(DISTINCT patient.patient_id) from patient JOIN visit ON patient.patient_id=visit.patient_id  JOIN encounter ON encounter.visit_id = visit.visit_id JOIN obs ON obs.encounter_id=encounter.encounter_id  WHERE visit.voided=0 AND patient.voided=0 AND obs.concept_id=165160 AND (obs.value_coded IN (165161,165162,165163)) AND encounter_type=12 AND visit.location_id=:locationdId")
    LiveData<Long> getTotalScreened(long locationdId);

    @Override
    @Query("SELECT patient_id FROM (SELECT * FROM patient WHERE patient_id NOT IN (:id)) WHERE patient_id >= :offsetId")
    Long[] findEntityNotWithId(long offsetId, long... id);



}