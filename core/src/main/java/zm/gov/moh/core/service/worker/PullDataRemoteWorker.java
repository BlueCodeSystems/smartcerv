package zm.gov.moh.core.service.worker;

import android.content.Context;
import android.content.Intent;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.domain.PatientEntity;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.ServiceManager;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class PullDataRemoteWorker extends RemoteWorker {

    int tasksCompleted = 0;
    int tasksStarted = 8;

    Result result = Result.success();

    List<Long> localPatientIds;





    public PullDataRemoteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams){
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

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

        return this.result;
    }

    public void onTaskCompleted(){

        tasksCompleted++;

        if(tasksCompleted == tasksStarted) {
            repository.getDefaultSharePrefrences().edit().putString(Key.LAST_SYNC_DATE,LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).apply();
            mBundle.putSerializable(Key.ENTITY_TYPE, EntityType.VISIT);
                ServiceManager.getInstance(getApplicationContext())
                        .setService(ServiceManager.Service.PUSH_ENTITY_REMOTE)
                        .putExtras(mBundle)
                        .start();
        }
    }


    public void getObs(String accessToken,long locationId,LocalDateTime MIN_DATETIME,final long offset, int limit ){

        ConcurrencyUtils.consumeBlocking(
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

        ConcurrencyUtils.consumeBlocking(

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

        ConcurrencyUtils.consumeBlocking(

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

        ConcurrencyUtils.consumeBlocking(

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

        ConcurrencyUtils.consumeBlocking(

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

        ConcurrencyUtils.consumeBlocking(

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
}