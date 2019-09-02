package zm.gov.moh.core.service.worker;

import android.content.Context;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import io.reactivex.Observable;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.domain.PatientEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class PullDataRemoteWorker extends RemoteWorker {

    List<Long> localPatientIds;

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
            ConcurrencyUtils.consumeAsync(

                    patient -> {
                        List<Long> indexIds = db.patientDao().getIds();
                        List<PatientEntity> patientEntities = Observable.fromArray(patient)
                                .filter((patientEntity -> (!localPatientIds.contains(patientEntity.getPatientId()) && !indexIds.contains(patientEntity.getPatientId()))))
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

                        db.personDao().insert(persons);
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

                        db.personNameDao().insert(personNames);
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
}