package zm.gov.moh.core.service;

import android.content.Intent;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.SynchronizableEntity;
import zm.gov.moh.core.repository.database.entity.domain.PatientEntity;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.utils.ConcurrencyUtils;

@Deprecated
public class PullDataRemote extends RemoteService {

    int tasksCompleted = 0;
    int tasksStarted = 8;
    public PullDataRemote(){
        super(ServiceManager.Service.PULL_ENTITY_REMOTE);
    }

    List<Long> localPatientIds;

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        super.onHandleIntent(intent);
    }

    @Override
    protected void executeAsync() {

        String lastSyncDate = repository.getDefaultSharePrefrences().getString(Key.LAST_SYNC_DATE,null);

        if(lastSyncDate != null)
            MIN_DATETIME = LocalDateTime.parse(lastSyncDate);


        //Patient identifier
        ConcurrencyUtils.consumeBlocking(
                patientIdentifierEntities -> {

                    List<String> localIdentifiers = db.patientIdentifierDao().getLocal();


                    localPatientIds = Observable.fromArray(patientIdentifierEntities).filter(
                            patientIdentifierEntity -> localIdentifiers.contains(patientIdentifierEntity.getIdentifier()))
                            .map(patientIdentifierEntity -> patientIdentifierEntity.getPatientId())
                            .toList()
                            .blockingGet();

                    repository.getDatabase().patientIdentifierDao().insert(patientIdentifierEntities);
                }, //consumer
                this::onError,
                repository.getRestApi().getPatientIdentifiers(accessToken), //producer
                TIMEOUT);
        onTaskCompleted();


        //Patients
        ConcurrencyUtils.consumeBlocking(


                patient -> {
                    List<Long> indexIds = db.patientDao().getIds();
                    List<PatientEntity> patientEntities = Observable.fromArray(patient)
                            .filter((patientEntity -> (!localPatientIds.contains(patientEntity.getPatientId()) && !indexIds.contains(patientEntity.getPatientId()))))
                            .toList()
                            .blockingGet();

                    repository.getDatabase().patientDao().insert(patientEntities);
                }, //consumer
                this::onError,
                repository.getRestApi().getPatients(accessToken), //producer
                TIMEOUT);
        onTaskCompleted();


        //Person
        /*ConcurrencyUtils.consumeBlocking(
                persons -> {
                    List<Long> indexIds = db.personDao().getIds();
                    List<Person> personList = Observable.fromArray(persons)
                            .map(person -> {

                                db.personIdentifierDao().insert(new PersonIdentifier(person));
                                return person;
                            })
                            .filter((person -> (!localPatientIds.contains(person.getPersonId()) && !indexIds.contains(person.getPersonId()))))
                            .toList()
                            .blockingGet();

                    repository.getDatabase().personDao().insert(personList);
                }, //consumer
                this::onError,
                repository.getRestApi().getPersons(accessToken), //producer
                TIMEOUT);*/



            //obs
            //lastModified = db.entityMetadataDao().findEntityLatestModifiedById(EntityType.OBS.getId());
           // if(lastModified == null) lastModified = MIN_DATETIME;
            getObs(accessToken,locationId, MIN_DATETIME,OFFSET, LIMIT);

            //visit
           //// lastModified = db.entityMetadataDao().findEntityLatestModifiedById(EntityType.VISIT.getId());
            //if(lastModified == null) lastModified = MIN_DATETIME;
            getVisit(accessToken,locationId, MIN_DATETIME, OFFSET,LIMIT);

            //encounter
            //lastModified = db.entityMetadataDao().findEntityLatestModifiedById(EntityType.ENCOUNTER.getId());
            //if(lastModified == null) lastModified = MIN_DATETIME;
            getEncounter(accessToken,locationId, MIN_DATETIME,OFFSET, LIMIT);

            //person
           // lastModified = db.entityMetadataDao().findEntityLatestModifiedById(EntityType.PERSON.getId());
          //  if(lastModified == null) lastModified = MIN_DATETIME;
            getPersons(accessToken,locationId, MIN_DATETIME,OFFSET, LIMIT);

            //person name
            //lastModified = db.entityMetadataDao().findEntityLatestModifiedById(EntityType.PERSON_NAME.getId());
            //if(lastModified == null) lastModified = MIN_DATETIME;
            getPersonNames(accessToken,locationId, MIN_DATETIME,OFFSET, LIMIT);

            //person address
            //lastModified = db.entityMetadataDao().findEntityLatestModifiedById(EntityType.PERSON_ADDRESS.getId());
            //if(lastModified == null) lastModified = MIN_DATETIME;
            getPersonAddress(accessToken,locationId, MIN_DATETIME,OFFSET, LIMIT);


        //}

        //onTaskCompleted();
    }

    @Override
    public void onTaskCompleted(){

        tasksCompleted++;

        if(tasksCompleted == tasksStarted) {
            notifyCompleted();
            repository.getDefaultSharePrefrences().edit().putString(Key.LAST_SYNC_DATE,LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).apply();
            mBundle.putSerializable(Key.ENTITY_TYPE, EntityType.VISIT);
                ServiceManager.getInstance(getApplicationContext())
                        .setService(ServiceManager.Service.PUSH_ENTITY_REMOTE)
                        .putExtras(mBundle)
                        .start();
        }
    }


    public void getObs(String accessToken,long locationId,LocalDateTime MIN_DATETIME,final long offset, int limit){

        ConcurrencyUtils.consumeAsync(
                obs -> {

                    if(obs.length > 0){

                        db.obsDao().insert(obs);
                        updateMetadata(obs, EntityType.OBS);
                        getObs(accessToken,locationId,MIN_DATETIME,offset + limit,limit);
                    }else
                        this.onTaskCompleted();

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
                        this.onTaskCompleted();

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
                        this.onTaskCompleted();

                }, //consumer
                this::onError,
                repository.getRestApi().getEncounters(accessToken,locationId,MIN_DATETIME,offset,limit), //producer
                TIMEOUT);
    }

    public void getPersons(String accessToken,long locationId,LocalDateTime MIN_DATETIME,final long offset, int limit){

        ConcurrencyUtils.consumeAsync(

                persons -> {

                    if(persons.length > 0){

                        db.personDao().insert(persons);
                        updateMetadata(persons, EntityType.PERSON);
                        getPersons(accessToken,locationId,MIN_DATETIME,offset + limit,limit);
                    }else {
                        List<Person> people = db.personDao().getAllT();
                        this.onTaskCompleted();

                    }

                }, //consumer
                this::onError,
                repository.getRestApi().getPersons(accessToken,locationId,MIN_DATETIME,offset,limit), //producer
                TIMEOUT);
    }


    public void getPersonNames(String accessToken,long locationId,LocalDateTime MIN_DATETIME,final long offset, int limit){

        ConcurrencyUtils.consumeAsync(

                personNames -> {

                    if(personNames.length > 0){

                        db.personNameDao().insert(personNames);
                        updateMetadata(personNames, EntityType.PERSON_NAME);
                        getPersonNames(accessToken,locationId,MIN_DATETIME,offset + limit, limit);
                    }else
                        this.onTaskCompleted();

                }, //consumer
                this::onError,
                repository.getRestApi().getPersonNames(accessToken,locationId,MIN_DATETIME,offset,limit), //producer
                TIMEOUT);
    }

    public void getPersonAddress(String accessToken,long locationId,LocalDateTime MIN_DATETIME,final long offset, int limit){

        ConcurrencyUtils.consumeAsync(

                personAddresses -> {

                    if(personAddresses.length > 0){

                        db.personAddressDao().insert(personAddresses);
                        updateMetadata(personAddresses, EntityType.PERSON_ADDRESS);

                        getPersonAddress(accessToken,locationId, MIN_DATETIME, offset + limit, limit);
                    }else
                        this.onTaskCompleted();

                }, //consumer
                this::onError,
                repository.getRestApi().getPersonAddresses(accessToken,locationId,MIN_DATETIME,offset,limit), //producer
                TIMEOUT);
    }


    public void persistPatientIdentifier(PatientIdentifierEntity... patientIdentifiers){


    }

    /*public LocalDateTime getLatestDate(SynchronizableEntity[] synchronizableEntities){

        LocalDateTime latestDate = lastModified;

        for (SynchronizableEntity entity: synchronizableEntities){
            LocalDateTime dateCreated = entity.getDateCreated();
            LocalDateTime dateChanged = entity.getDateCreated();

            if(dateCreated != null && dateCreated.isAfter(latestDate))
                latestDate = dateCreated;

            if(dateChanged != null  && dateChanged.isAfter(latestDate))
                latestDate = dateChanged;
        }

        return latestDate;
    }*/

}