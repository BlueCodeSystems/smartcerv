package zm.gov.moh.core.service.worker;

import android.content.Context;
import android.util.LongSparseArray;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import io.reactivex.Observable;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.derived.PersonIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.PatientEntity;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class PullDataRemoteWorker extends RemoteWorker {

    HashMap<Long,Long> remoteLocalIdentifierMap = new HashMap<>();

    public PullDataRemoteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams){
        super(context, workerParams);

    }

    @NonNull
    @Override
    public Result doWork() {

        taskPoolSize = 8;

        String lastSyncDate = repository.getDefaultSharePrefrences().getString(Key.LAST_DATA_SYNC_DATETIME,null);

        if(lastSyncDate != null)
            MIN_DATETIME = LocalDateTime.parse(lastSyncDate);

        LocalDateTime localDateTime =  LocalDateTime.parse("1970-01-01T00:00:00");

            //Patient identifier
            ConcurrencyUtils.consumeBlocking(
                    patientIdentifierEntities -> {

                        Observable.fromArray(patientIdentifierEntities)
                                .filter(patientIdentifierEntity -> patientIdentifierEntity.getIdentifierType() == 3)
                                .map(this::updateIdentifiers)
                                .toList()
                                .blockingGet();

                        //db.personIdentifierDao().insert(personIdentifiers);

                        db.patientIdentifierDao().insert(patientIdentifierEntities);
                    }, //consumer
                    this::onError,
                    repository.getRestApi().getPatientIdentifiers(accessToken,locationId, localDateTime,OFFSET, LIMIT), //producer
                    TIMEOUT);
        onTaskCompleted();


            //Patients
            ConcurrencyUtils.consumeAsync(

                    patient -> {

                        List<PatientEntity> patientEntities = Observable.fromArray(patient)
                                .filter((patientEntity -> (!remoteLocalIdentifierMap.keySet().contains(patientEntity.getPatientId()))))
                                .toList()
                                .blockingGet();

                        repository.getDatabase().patientDao().insert(patientEntities);
                        onTaskCompleted();
                    }, //consumer
                    this::onError,
                    repository.getRestApi().getPatients(accessToken), //producer
                    TIMEOUT);

            //obs
            getObs(accessToken,locationId, MIN_DATETIME,OFFSET, LIMIT);

            //visit
            getVisit(accessToken,locationId, MIN_DATETIME, OFFSET,LIMIT);

            //encounter
            getEncounter(accessToken,locationId, MIN_DATETIME,OFFSET, LIMIT);

            //person
            getPersons(accessToken,locationId, MIN_DATETIME,OFFSET, LIMIT);

            //person name
            getPersonNames(accessToken,locationId, MIN_DATETIME,OFFSET, LIMIT);

            //person address
            getPersonAddress(accessToken,locationId, MIN_DATETIME,OFFSET, LIMIT);


        if(awaitResult().equals(Result.success())){

            repository.getDefaultSharePrefrences().edit()
                    .putString(Key.LAST_DATA_SYNC_DATETIME, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .apply();
        }

        return awaitResult();
    }




    public void getObs(String accessToken,long locationId,LocalDateTime MIN_DATETIME,final long offset, int limit ){

        ConcurrencyUtils.consumeAsync(
                obs -> {

                    if(obs.length > 0){

                        db.obsDao().insert(obs);
                        updateMetadata(obs, EntityType.OBS);
                        getObs(accessToken,locationId,MIN_DATETIME,offset + limit,limit);
                    }else
                        onTaskCompleted();

                }, //consumer
                this::onError,
                repository.getRestApi().getObs(accessToken,locationId,MIN_DATETIME,offset,limit), //producer
                TIMEOUT);
    }

    public void getVisit(String accessToken,long locationId,LocalDateTime MIN_DATETIME,final long offset, int limit){

        ConcurrencyUtils.consumeAsync(

                visitEntities -> {

                    if(visitEntities.length > 0){

                        db.visitDao().insert(visitEntities);
                        updateMetadata(visitEntities, EntityType.VISIT);
                        getVisit(accessToken,locationId,MIN_DATETIME,offset + limit,limit);
                    }else
                        onTaskCompleted();

                }, //consumer
                this::onError,
                repository.getRestApi().getVisit(accessToken,locationId,MIN_DATETIME,offset,limit), //producer
                TIMEOUT);
    }

    public void getEncounter(String accessToken,long locationId,LocalDateTime MIN_DATETIME, final long offset,int limit){

        ConcurrencyUtils.consumeAsync(

                encounterEntities -> {

                    if(encounterEntities.length > 0){

                        db.encounterDao().insert(encounterEntities);
                        updateMetadata(encounterEntities, EntityType.ENCOUNTER);
                        getEncounter(accessToken,locationId,MIN_DATETIME,offset + limit,limit);
                    }else
                        onTaskCompleted();

                }, //consumer
                this::onError,
                repository.getRestApi().getEncounters(accessToken,locationId,MIN_DATETIME,offset,limit), //producer
                TIMEOUT);
    }

    public void getPersons(String accessToken,long locationId,LocalDateTime MIN_DATETIME,final long offset, int limit){

        ConcurrencyUtils.consumeAsync(

                persons -> {

                    if(persons.length > 0){

                        for(Person person: persons){

                            if(remoteLocalIdentifierMap.keySet().contains(person.getPersonId())) {

                                Person person1 = db.personDao().findById(remoteLocalIdentifierMap.get(person.getPersonId()));
                                person.setPersonId(person1.getPersonId());
                            }
                            db.personDao().insert(person);
                        }
                        updateMetadata(persons, EntityType.PERSON);
                        getPersons(accessToken,locationId,MIN_DATETIME,offset + limit,limit);
                    }else
                        onTaskCompleted();


                }, //consumer
                this::onError,
                repository.getRestApi().getPersons(accessToken,locationId,MIN_DATETIME,offset,limit), //producer
                TIMEOUT);
    }


    public void getPersonNames(String accessToken,long locationId,LocalDateTime MIN_DATETIME,final long offset, int limit){

        ConcurrencyUtils.consumeAsync(

                personNames -> {

                    if(personNames.length > 0){

                        for(PersonName personName: personNames){
                            if(remoteLocalIdentifierMap.keySet().contains(personName.getPersonId())) {

                                PersonName personName1 = db.personNameDao().findPersonNameById(remoteLocalIdentifierMap.get(personName.getPersonId()));
                                personName.setPersonNameId(personName1.getPersonNameId());
                                personName.setPersonId(personName1.getPersonId());
                            }
                            db.personNameDao().insert(personNames);
                        }

                        updateMetadata(personNames, EntityType.PERSON_NAME);
                        getPersonNames(accessToken,locationId,MIN_DATETIME,offset + limit, limit);
                    }else
                        onTaskCompleted();

                }, //consumer
                this::onError,
                repository.getRestApi().getPersonNames(accessToken,locationId,MIN_DATETIME,offset,limit), //producer
                TIMEOUT);
    }

    public void getPersonAddress(String accessToken,long locationId,LocalDateTime MIN_DATETIME,final long offset, int limit){

        ConcurrencyUtils.consumeAsync(

                personAddresses -> {

                    if(personAddresses.length > 0){

                        for(PersonAddress personAddress: personAddresses){

                            if(remoteLocalIdentifierMap.keySet().contains(personAddress.getPersonId())) {

                                PersonAddress personAddress1 = db.personAddressDao().findByPersonId(remoteLocalIdentifierMap.get(personAddress.getPersonId()));
                                personAddress.setPersonAddressId(personAddress1.getPersonAddressId());
                                personAddress.setPersonId(personAddress1.getPersonId());

                            }
                            db.personAddressDao().insert(personAddress);
                        }

                        db.personAddressDao().insert(personAddresses);
                        updateMetadata(personAddresses, EntityType.PERSON_ADDRESS);

                        getPersonAddress(accessToken,locationId, MIN_DATETIME, offset + limit, limit);
                    }else
                        onTaskCompleted();

                }, //consumer
                this::onError,
                repository.getRestApi().getPersonAddresses(accessToken,locationId,MIN_DATETIME,offset,limit), //producer
                TIMEOUT);
    }

    public PatientIdentifierEntity updateIdentifiers(PatientIdentifierEntity patientIdentifierEntity){
        PersonIdentifier personIdentifier = db.personIdentifierDao().getByIdentifier(patientIdentifierEntity.getIdentifier());
        if(personIdentifier != null){
            personIdentifier.setRemoteId(patientIdentifierEntity.getPatientId());
            db.personIdentifierDao().insert(personIdentifier);
            if(personIdentifier.getLocalId() != null)
                remoteLocalIdentifierMap.put(patientIdentifierEntity.getPatientId(), personIdentifier.getLocalId());          // syncedPersonIdentifiers.add(patientIdentifierEntity.getPatientId());
        }

        return patientIdentifierEntity;
    }
}