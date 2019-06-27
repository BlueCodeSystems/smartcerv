package zm.gov.moh.core.service;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class PullDataRemote extends RemoteService {

    public PullDataRemote(){
        super(ServiceManager.Service.PULL_ENTITY_REMOTE);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        super.onHandleIntent(intent);
    }

    @Override
    protected void executeAsync() {

        //Person Names
        ConcurrencyUtils.consumeAsync(
                personNames -> {
                    repository.getDatabase().personNameDao().insert(personNames);
                    this.onTaskCompleted();
                },//consumer
                this::onError,
                repository.getRestApi().getPersonNames(accessToken), //producer
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
                repository.getRestApi().getPersonAddresses(accessToken), //producer
                TIMEOUT);
        onTaskStarted();

        //Person
        ConcurrencyUtils.consumeAsync(
                person -> {
                    repository.getDatabase().personDao().insert(person);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getPersons(accessToken), //producer
                TIMEOUT);
        onTaskStarted();

        //Patients
        ConcurrencyUtils.consumeAsync(
                patient -> {
                    repository.getDatabase().patientDao().insert(patient);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getPatients(accessToken), //producer
                TIMEOUT);
        onTaskStarted();

        //Patient identifier
        ConcurrencyUtils.consumeAsync(
                patientIdentifiers -> {
                    repository.getDatabase().patientIdentifierDao().insert(patientIdentifiers);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getPatientIdentifiers(accessToken), //producer
                TIMEOUT);
        onTaskStarted();

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

    @Override
    public void onTaskCompleted(){

        tasksCompleted++;

        if(tasksCompleted == tasksStarted) {
            notifyCompleted();


                ServiceManager.getInstance(getApplicationContext())
                        .setService(ServiceManager.Service.SUBSTITUTE_LOCAL_ENTITY)
                        .putExtras(mBundle)
                        .start();
        }
    }
}