package zm.gov.moh.core.repository.database.dao.derived;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;


import java.util.List;

import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.repository.database.entity.derived.Count;

@Dao
public interface CervicalCancerDao {

    @Query("SELECT COUNT(DISTINCT patient_id) AS count from patient")
    LiveData<Long> countPatients();

    @Query("SELECT patient.patient_id,patient_identifier.identifier, given_name, family_name, gender, birthdate FROM person JOIN person_name ON person.person_id = person_name.person_id JOIN patient ON patient.patient_id = person.person_id JOIN patient_identifier ON patient.patient_id = patient_identifier.patient_id WHERE person_name.preferred = 1 AND patient.voided = 0 AND identifier_type = 4")
    LiveData<List<Client>> getAllPatients();
}