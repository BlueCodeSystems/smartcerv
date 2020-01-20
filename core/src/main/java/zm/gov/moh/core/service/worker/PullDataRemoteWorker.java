package zm.gov.moh.core.service.worker;

import android.content.Context;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import io.reactivex.Observable;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.derived.PersonIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.domain.ObsEntity;
import zm.gov.moh.core.repository.database.entity.domain.PatientEntity;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class PullDataRemoteWorker extends RemoteWorker {

    HashMap<Long,Long> remoteLocalIdentifierMap = new HashMap<>();
    LocalDateTime localDateTime =  LocalDateTime.parse("1970-01-01T00:00:00");

    public PullDataRemoteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams){
        super(context, workerParams);

    }

    @NonNull
    @Override
    public Result doWork() {

        taskPoolSize = 8;

        if(lastDataSyncDate != null)
            MIN_DATETIME = LocalDateTime.parse(lastDataSyncDate);

            getPatientId(accessToken,locationId, localDateTime,OFFSET, LIMIT);

            getPatient(accessToken,locationId, localDateTime,OFFSET, LIMIT);

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
            getPersonAddress(accessToken,locationId, localDateTime,OFFSET, LIMIT);

        return awaitResult();
    }


    public void getPatient(String accessToken,long locationId,LocalDateTime MIN_DATETIME,final long offset, int limit){

        //Patients
        ConcurrencyUtils.consumeAsync(

                patient -> {

                    if(patient.length > 0) {
                        List<PatientEntity> patientEntities = Observable.fromArray(patient)
                                .filter((patientEntity -> (!remoteLocalIdentifierMap.keySet().contains(patientEntity.getPatientId()))))
                                .toList()
                                .blockingGet();

                        repository.getDatabase().patientDao().insert(patientEntities);
                        getPatient(accessToken,locationId,MIN_DATETIME,offset + limit,limit);
                    }
                    else
                        onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getPatients(accessToken,locationId, MIN_DATETIME,offset, limit), //producer
                TIMEOUT);
    }

    public void getPatientId(String accessToken,long locationId,LocalDateTime MIN_DATETIME,final long offset, int limit){

        ConcurrencyUtils.consumeBlocking(
                patientIdentifierEntities -> {

                    if(patientIdentifierEntities.length > 0) {

                        Observable.fromArray(patientIdentifierEntities)
                                .filter(patientIdentifierEntity -> patientIdentifierEntity.getIdentifierType() == 3)
                                .map(this::updateIdentifiers)
                                .toList()
                                .blockingGet();

                        //db.personIdentifierDao().insert(personIdentifiers);

                        db.patientIdentifierDao().insert(patientIdentifierEntities);
                        getPatientId(accessToken,locationId,MIN_DATETIME,offset + limit,limit);
                    }else
                        onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getPatientIdentifiers(accessToken,locationId, MIN_DATETIME,offset, limit), //producer
                TIMEOUT);
    }

    public void getObs(String accessToken,long locationId,LocalDateTime MIN_DATETIME,final long offset, int limit ){

        ConcurrencyUtils.consumeAsync(
                obs -> {

                    if(obs.length > 0){

                        for(ObsEntity obsEntity : obs)
                            if(db.obsDao().findByObsDatetime(obsEntity.getObsDateTime()).length == 0)
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

                        for(VisitEntity visitEntity : visitEntities)
                            if(db.visitDao().getByDatetime(visitEntity.getDateStarted()).length == 0)
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

                        for(EncounterEntity encounterEntity : encounterEntities)
                            if(db.encounterDao().getByDatetime(encounterEntity.getEncounterDatetime()).length == 0)
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

                            PersonIdentifier personIdentifier = db.personIdentifierDao().getByPersonId(person.getPersonId());
                            if(remoteLocalIdentifierMap.keySet().contains(person.getPersonId())) {

                                Person personLocal = db.personDao().findById(remoteLocalIdentifierMap.get(person.getPersonId()));

                                person.setPersonId(personLocal.getPersonId());

                                if((personLocal.getVoided() != null) && personLocal.getVoided() == 1)
                                    person.setVoided(personLocal.getVoided());
                            }

                            if(personIdentifier != null) {
                                personIdentifier.setRemoteUuid(person.getUuid());
                                db.personIdentifierDao().insert(personIdentifier);
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

                                PersonName personName1 = db.personNameDao().findByPersonId(remoteLocalIdentifierMap.get(personName.getPersonId()));
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

                                Long id = remoteLocalIdentifierMap.get(personAddress.getPersonId());

                                PersonAddress personAddress1 = db.personAddressDao().findByPersonId(id);

                                if(personAddress1 == null)
                                    continue;

                                personAddress1.setAddress1(personAddress.getAddress1());
                                db.personAddressDao().insert(personAddress1);
                                continue;
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
            if(personIdentifier.getLocalId() != null)
                remoteLocalIdentifierMap.put(patientIdentifierEntity.getPatientId(), personIdentifier.getLocalId());          // syncedPersonIdentifiers.add(patientIdentifierEntity.getPatientId());
        }
        else
             personIdentifier = new PersonIdentifier(patientIdentifierEntity.getIdentifier(),null, patientIdentifierEntity.getPatientId());

        db.personIdentifierDao().insert(personIdentifier);

        return patientIdentifierEntity;
    }
}