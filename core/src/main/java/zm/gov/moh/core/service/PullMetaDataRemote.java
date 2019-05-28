package zm.gov.moh.core.service;

import android.content.Intent;

import androidx.annotation.Nullable;

public class PullMetaDataRemote extends RemoteService {

    public PullMetaDataRemote(){
        super(ServiceManager.SERVICE_PULL_META_DATA_REMOTE);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        super.onHandleIntent(intent);
        //Get from rest API and insert into database asynchronously
        //Location
        repository.consumeAsync(

                locations ->{
                    repository.getDatabase().locationDao().insert(locations);
                    this.onTaskCompleted();
                },//consumer
                this::onError,
                repository.getRestApi().getLocations(accesstoken),
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
                repository.getRestApi().getLocationAttributes(accesstoken), //producer
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
                repository.getRestApi().getLocationAttributeTypes(accesstoken), //producer
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
                repository.getRestApi().getLocationTags(accesstoken), //producer
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
                repository.getRestApi().getLocationTagMaps(accesstoken), //producer
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
                repository.getRestApi().getUsers(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();


        //Patient identifier types
        repository.consumeAsync(
                patientIdentifierTypes ->{
                    repository.getDatabase().patientIdentifierTypeDao().insert(patientIdentifierTypes);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getPatientIdentifierTypes(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Providers
        repository.consumeAsync(
                providers ->{
                    repository.getDatabase().providerDao().insert(providers);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getProviders(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Concept name
        repository.consumeAsync(
                conceptNames ->{
                    repository.getDatabase().conceptNameDao().insert(conceptNames);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getConceptNames(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Concept answer
        repository.consumeAsync(
                conceptAnswers ->{
                    repository.getDatabase().conceptAnswerDao().insert(conceptAnswers);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getConceptAnswers(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Encounter types
        repository.consumeAsync(
                encounterTypes ->{
                    repository.getDatabase().encounterTypeDao().insert(encounterTypes);
                    this.onTaskCompleted();

                }, //consumer
                this::onError,
                repository.getRestApi().getEncounterTypes(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        //Concept
        repository.consumeAsync(
                visitTypes ->{
                    repository.getDatabase().visitTypeDao().insert(visitTypes);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getVisitTypes(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();

        repository.consumeAsync(
                concepts ->{
                    repository.getDatabase().conceptDao().insert(concepts);
                    this.onTaskCompleted();

                }, //consumer
                this::onError,
                repository.getRestApi().getConcept(accesstoken), //producer
                TIMEOUT);
        onTaskStarted();
    }

    @Override
    protected void executeAsync() {

    }
}
