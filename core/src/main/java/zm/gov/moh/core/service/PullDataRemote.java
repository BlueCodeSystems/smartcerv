package zm.gov.moh.core.service;

import android.content.Intent;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import zm.gov.moh.core.Constant;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class PullDataRemote extends RemoteService {

    public PullDataRemote(){
        super(ServiceManager.Service.PULL_ENTITY_REMOTE);
    }

    final String MIN_DATETIME = "1970-01-01T00:00:00";

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        super.onHandleIntent(intent);
    }

    @Override
    protected void executeAsync() {

        String lastSyncDate = repository.getDefaultSharePrefrences().getString(Key.LAST_SYNC_DATE,null);

        if(lastSyncDate == null){
            lastSyncDate = MIN_DATETIME;
        }

        //Person Names
        ConcurrencyUtils.consumeAsync(
                personNames -> {
                    repository.getDatabase().personNameDao().insert(personNames);
                    this.onTaskCompleted();
                },//consumer
                this::onError,
                repository.getRestApi().getPersonNames(accessToken, lastSyncDate), //producer
                TIMEOUT);
        onTaskStarted();

        //Person Address
        ConcurrencyUtils.consumeAsync(
                personAddresses -> {
                    repository.getDatabase().personAddressDao().insert(personAddresses);
                    this.onTaskCompleted();
                }
                , //consumer
                this::onError,
                repository.getRestApi().getPersonAddresses(accessToken, lastSyncDate), //producer
                TIMEOUT);
        onTaskStarted();

        //Person
        ConcurrencyUtils.consumeAsync(
                person -> {
                    repository.getDatabase().personDao().insert(person);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getPersons(accessToken, lastSyncDate), //producer
                TIMEOUT);
        onTaskStarted();

        //Patients
        ConcurrencyUtils.consumeAsync(
                patient -> {
                    repository.getDatabase().patientDao().insert(patient);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getPatients(accessToken, lastSyncDate), //producer
                TIMEOUT);
        onTaskStarted();

        //Patient identifier
        ConcurrencyUtils.consumeAsync(
                patientIdentifiers -> {
                    repository.getDatabase().patientIdentifierDao().insert(patientIdentifiers);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getPatientIdentifiers(accessToken, lastSyncDate), //producer
                TIMEOUT);
        onTaskStarted();


        if(lastSyncDate.equals(MIN_DATETIME)) {

            //Observations
            ConcurrencyUtils.consumeAsync(
                    obs -> {
                        repository.getDatabase().obsDao().insert(obs);
                        this.onTaskCompleted();
                    }, //consumer
                    this::onError,
                    repository.getRestApi().getObs(accessToken), //producer
                    TIMEOUT);
            onTaskStarted();

            //Visit
            ConcurrencyUtils.consumeAsync(
                    visits -> {
                        repository.getDatabase().visitDao().insert(visits);
                        this.onTaskCompleted();
                    }, //consumer
                    this::onError,
                    repository.getRestApi().getVisit(accessToken), //producer
                    TIMEOUT);
            onTaskStarted();

            //Encounter
            ConcurrencyUtils.consumeAsync(
                    encounterEntities -> {
                        repository.getDatabase().encounterDao().insert(encounterEntities);
                        this.onTaskCompleted();
                    }, //consumer
                    this::onError,
                    repository.getRestApi().getEncounters(accessToken), //producer
                    TIMEOUT);
            onTaskStarted();
        }
    }

    @Override
    public void onTaskCompleted(){

        tasksCompleted++;

        if(tasksCompleted == tasksStarted) {
            notifyCompleted();
            repository.getDefaultSharePrefrences().edit().putString(Key.LAST_SYNC_DATE,LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).apply();
                ServiceManager.getInstance(getApplicationContext())
                        .setService(ServiceManager.Service.SUBSTITUTE_LOCAL_ENTITY)
                        .putExtras(mBundle)
                        .start();
        }
    }
}