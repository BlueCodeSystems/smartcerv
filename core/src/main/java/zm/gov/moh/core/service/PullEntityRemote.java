package zm.gov.moh.core.service;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import zm.gov.moh.core.model.IntentAction;

public class PullEntityRemote extends RemoteService {

    public PullEntityRemote(){
        super(ServiceManager.Service.PULL_ENTITY_REMOTE);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        super.onHandleIntent(intent);

        //Person Names
        repository.consumeAsync(
                personNames -> {
                    repository.getDatabase().personNameDao().insert(personNames);
                    this.onTaskCompleted();
                },//consumer
                this::onError,
                repository.getRestApi().getPersonNames(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Person Address
        repository.consumeAsync(
                personAddresses -> {
                    repository.getDatabase().personAddressDao().insert(personAddresses);
                    this.onTaskCompleted();
                }
                , //consumer
                this::onError,
                repository.getRestApi().getPersonAddresses(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Person
        repository.consumeAsync(
                person -> {
                    repository.getDatabase().personDao().insert(person);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getPersons(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Patients
        repository.consumeAsync(
                patient -> {
                    repository.getDatabase().patientDao().insert(patient);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getPatients(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Patient identifier
        repository.consumeAsync(
                patientIdentifiers -> {
                    repository.getDatabase().patientIdentifierDao().insert(patientIdentifiers);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getPatientIdentifiers(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Observations
        repository.consumeAsync(
                obs -> {
                    repository.getDatabase().obsDao().insert(obs);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getObs(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();
    }

    @Override
    protected void notifySyncCompleted() {
        Intent intent = new Intent(ServiceManager.IntentAction.PULL_ENTITY_REMOTE_COMPLETE);
        intent.putExtras(mBundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onError(Throwable throwable) {
        super.onError(throwable);

        Intent intent = new Intent(ServiceManager.IntentAction.PULL_ENTITY_REMOTE_INTERRUPT);
        intent.putExtras(mBundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    protected void executeAsync() {

    }
}