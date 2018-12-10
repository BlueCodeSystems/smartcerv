package zm.gov.moh.core.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import com.jakewharton.threetenabp.AndroidThreeTen;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.InjectorUtils;

public class MetaDataSync extends IntentService implements InjectableViewModel {

    private Repository repository;
    private String accesstoken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7InV1aWQiOiJhM2FjYTE4MS1kMDJhLTRiODgtOTc1NC1lYWM0NWQzZGUzZmUiLCJkaXNwbGF5IjoiYW50aG9ueSIsInVzZXJuYW1lIjoiYW50aG9ueSIsInN5c3RlbUlkIjoiMy00In0sImlhdCI6MTU0MjE0MzU3NiwiZXhwIjoxNTkyMTQzNTc2fQ.DsDbPXwaZ5sg2SFCq1CBykITJjog-9u-4XzNGw9IYV8";
    private final int TIMEOUT = 30000;
    private int tasksCompleted = 0;
    private int tasksStarted = 0;

    public MetaDataSync(){
        super("");


    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        AndroidThreeTen.init(this);
        InjectorUtils.provideRepository(this,getApplication());

        //Get from rest API and insert into database asynchronously

        //Location
        repository.consumeAsync(
                locations ->{
                    repository.getDatabase().locationDao().insert(locations);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApiAdapter().getLocations(accesstoken),
                 //producer
                TIMEOUT);
        onTaskStarted();

       //Location attributes
        repository.consumeAsync(
                locationAttributes -> {
                    repository.getDatabase().locationAttributeDao().insert(locationAttributes);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApiAdapter().getLocationAttributes(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Location attributes types
        repository.consumeAsync(
                locationAttributeTypes -> {
                    repository.getDatabase().locationAttributeTypeDao().insert(locationAttributeTypes);
                    this.onTaskCompleted();
                } , //consumer
                this::onError,
                this::onTaskCompleted,
                repository.getRestApiAdapter().getLocationAttributeTypes(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Location tag types
        repository.consumeAsync(
                locationTag -> {
                    repository.getDatabase().locationTagDao().insert(locationTag);
                    this.onTaskCompleted();
                } , //consumer
                this::onError,
                this::onTaskCompleted,
                repository.getRestApiAdapter().getLocationTags(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Location tag map types
        repository.consumeAsync(
                locationTagMap -> {
                    repository.getDatabase().locationTagMapDao().insert(locationTagMap);
                    this.onTaskCompleted();
                } , //consumer
                this::onError,
                this::onTaskCompleted,
                repository.getRestApiAdapter().getLocationTagMaps(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Users
        repository.consumeAsync(
                users ->{
                    repository.getDatabase().userDao().insert(users);
                    this.onTaskCompleted();
                },  //consumer
                this::onError,
                this::onTaskCompleted,
                repository.getRestApiAdapter().getUsers(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Person Names
        repository.consumeAsync(
                personNames ->{
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
                patientIdentifiers->{
                    repository.getDatabase().patientIdentifierDao().insert(patientIdentifiers);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApiAdapter().getPatientIdentifiers(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Patient identifier types
        repository.consumeAsync(
                patientIdentifierTypes ->{
                    repository.getDatabase().patientIdentifierTypeDao().insert(patientIdentifierTypes);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApiAdapter().getPatientIdentifierTypes(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Providers
        repository.consumeAsync(
                providers ->{
                    repository.getDatabase().providerDao().insert(providers);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApiAdapter().getProviders(accesstoken), //producer
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

    @Override
    public void setRepository(Repository repository) {
        this.repository = repository;
    }


    public void onError(Throwable throwable){
        Exception e = new Exception(throwable);
    }



    private void notifySyncCompleted(){
        Intent intent = new Intent("zm.gov.moh.common.SYNC_COMPLETE_NOTIFICATION");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void onTaskStarted(){
        tasksStarted++;
    }

    public void onTaskCompleted(){

        tasksCompleted++;

        if(tasksCompleted == tasksStarted)
            notifySyncCompleted();
    }
}
