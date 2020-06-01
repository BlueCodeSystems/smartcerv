package zm.gov.moh.core.repository.database.dao.domain;

import org.threeten.bp.LocalDateTime;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import zm.gov.moh.core.model.LineChartVisitItem;
import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.PatientEntity;

@Dao
public interface PatientDao extends Synchronizable<Long> {

    //gets all persons attribute type
    @Query("SELECT * FROM patient")
    List<PatientEntity> getAll();

    @Query("SELECT MAX(datetime) AS datetime FROM (SELECT CASE WHEN COALESCE(date_created,'1970-01-01T00:00:00') >= COALESCE(date_changed,'1970-01-01T00:00:00') THEN date_created ELSE date_changed END datetime FROM patient WHERE patient_id IN (SELECT DISTINCT patient_id FROM patient_identifier WHERE location_id = :locationId) AND patient_id < :localLimit)")
    LocalDateTime getMaxDatetime(long locationId, long localLimit);

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


    // total number of patients seen
    @Query("SELECT COUNT(DISTINCT patient.patient_id) from patient JOIN patient_identifier ON patient.patient_id=patient_identifier.patient_id JOIN visit ON patient_identifier.patient_id=visit.patient_id WHERE patient_identifier.identifier_type=4 AND patient.voided=0 AND visit.voided=0 AND patient_identifier.location_id =:location_id ORDER BY patient.patient_id DESC")
    LiveData <Long> getTotalSeenClients(long location_id);

    //total number of patients seen by date

    @Query("SELECT COUNT(DISTINCT patient.patient_id) from patient JOIN patient_identifier ON patient.patient_id=patient_identifier.patient_id JOIN visit ON patient_identifier.patient_id=visit.patient_id WHERE patient_identifier.identifier_type=4 AND patient.voided=0 AND visit.voided=0 AND patient_identifier.location_id =:location_id AND date_started BETWEEN datetime('now',:days) AND datetime('now','+1 days') ORDER BY patient.patient_id DESC")
    LiveData <Long> getTotalSeenClientsByDays(long location_id,String days);

    //get total number of patients registered
        @Query("SELECT COUNT(DISTINCT patient.patient_id) from patient JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE identifier_type=4 AND patient.voided=0 AND patient_identifier.location_id=:location_id  ORDER BY patient.patient_id DESC")
    LiveData <Long> getTotalRegistered(long location_id);

        //get total registered according to date

    @Query("SELECT COUNT(DISTINCT patient.patient_id) from patient JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE identifier_type=4 AND patient.voided=0 AND patient_identifier.location_id=:location_id AND  patient_identifier.date_created BETWEEN datetime('now',:days) AND datetime('now','+1 days') ORDER BY patient.patient_id DESC")
    LiveData <Long> getTotalRegisteredClientsByDate(long location_id,String days);


        //get total number of patients screened
    @Query("SELECT COUNT(DISTINCT patient.patient_id) from patient JOIN visit ON patient.patient_id=visit.patient_id  JOIN encounter ON encounter.visit_id = visit.visit_id JOIN obs ON obs.encounter_id=encounter.encounter_id  WHERE visit.voided=0 AND patient.voided=0 AND obs.concept_id=165160 AND (obs.value_coded IN (165161,165162,165163)) AND encounter_type=12 AND visit.location_id=:locationdId")
    LiveData<Long> getTotalScreened(long locationdId);

    //get  total screened according to date
    @Query("SELECT COUNT(DISTINCT patient.patient_id) from patient JOIN visit ON patient.patient_id=visit.patient_id  JOIN encounter ON encounter.visit_id = visit.visit_id JOIN obs ON obs.encounter_id=encounter.encounter_id  WHERE visit.voided=0 AND patient.voided=0 AND obs.concept_id=165160 AND (obs.value_coded IN (165161,165162,165163)) AND encounter_type=12 AND date_started BETWEEN datetime('now',:days) AND datetime('now','+1 days')  AND visit.location_id=:locationdId")
    LiveData<Long> getTotalScreenedByDays(long locationdId,String days);

    //get total number of screens from last 5 days
    @Query("SELECT COUNT(DISTINCT visit.patient_id) AS `Count_patient_id`,DATE(visit.date_started) AS `dateStarted`,obs.value_coded AS `valueCoded` from patient JOIN visit ON patient.patient_id=visit.patient_id  JOIN encounter ON encounter.visit_id = visit.visit_id JOIN obs ON obs.encounter_id=encounter.encounter_id  WHERE visit.voided=0 AND patient.voided=0 AND obs.concept_id=165160 AND (obs.value_coded IN (165161,165162,165163)) AND encounter_type=12 AND  visit.location_id=:locationdId AND date_started BETWEEN datetime('now','-5 days') AND datetime('now','+1 days')  GROUP BY obs.value_coded, visit.date_started ORDER BY visit.date_started ASC")
    LiveData<List<LineChartVisitItem>>getTotalScreenedLineChart(long locationdId);

    @Override
    @Query("SELECT patient_id FROM (SELECT * FROM patient WHERE patient_id NOT IN (:id)) WHERE patient_id >= :offsetId")
    Long[] findEntityNotWithId(long offsetId, long... id);

    @Query("SELECT patient_id FROM (SELECT * FROM patient WHERE NOT EXISTS (SELECT * FROM patient WHERE patient_id NOT IN (SELECT DISTINCT entity_id FROM entity_metadata WHERE entity_type_id = :entityTypeId AND remote_status_code = :remoteStatus))) WHERE patient_id >= :offsetId")
    Long[] findEntityNotWithId2(long offsetId, int entityTypeId, short remoteStatus);

}