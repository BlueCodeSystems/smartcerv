package zm.gov.moh.core.service;

import android.content.Intent;
import androidx.annotation.Nullable;

public class DataSync extends SyncService  {

    public DataSync(){
        super(ServiceName.DATA_SYNC);
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
                repository.getRestApiAdapter().getPersonNames(accesstoken), //producer
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
                repository.getRestApiAdapter().getPersonAddresses(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Person
        repository.consumeAsync(
                person -> {
                    repository.getDatabase().personDao().insert(person);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApiAdapter().getPersons(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Patients
        repository.consumeAsync(
                patient -> {
                    repository.getDatabase().patientDao().insert(patient);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApiAdapter().getPatients(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Patient identifier
        repository.consumeAsync(
                patientIdentifiers -> {
                    repository.getDatabase().patientIdentifierDao().insert(patientIdentifiers);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApiAdapter().getPatientIdentifiers(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Observations
        repository.consumeAsync(
                obs -> {
                    repository.getDatabase().obsDao().insert(obs);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApiAdapter().getObs(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();
    }
}