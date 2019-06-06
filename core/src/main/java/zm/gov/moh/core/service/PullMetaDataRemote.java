package zm.gov.moh.core.service;

import android.content.Intent;

import java.util.List;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import zm.gov.moh.core.utils.ConcurrencyUtils;
import zm.gov.moh.core.repository.database.entity.domain.ConceptAnswer;

public class PullMetaDataRemote extends RemoteService {

    public PullMetaDataRemote(){
        super(ServiceManager.Service.PULL_META_DATA_REMOTE);
    }

    @Override
    protected void executeAsync() {
        //Get from rest API and insert into database asynchronously
        //Location
        ConcurrencyUtils.consumeAsync(

                locations ->{
                    repository.getDatabase().locationDao().insert(locations);
                    this.onTaskCompleted();
                },//consumer
                this::onError,
                repository.getRestApi().getLocations(accessToken),
                //producer
                TIMEOUT);
        onTaskStarted();

        //Location attributes
        ConcurrencyUtils.consumeAsync(
                locationAttributes -> {
                    repository.getDatabase().locationAttributeDao().insert(locationAttributes);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getLocationAttributes(accessToken), //producer
                TIMEOUT);
        onTaskStarted();

        //Location attributes types
        ConcurrencyUtils.consumeAsync(
                locationAttributeTypes -> {
                    repository.getDatabase().locationAttributeTypeDao().insert(locationAttributeTypes);
                    this.onTaskCompleted();
                } , //consumer
                this::onError,
                this::onTaskCompleted,
                repository.getRestApi().getLocationAttributeTypes(accessToken), //producer
                TIMEOUT);
        onTaskStarted();

        //Location tag types
        ConcurrencyUtils.consumeAsync(
                locationTag -> {
                    repository.getDatabase().locationTagDao().insert(locationTag);
                    this.onTaskCompleted();
                } , //consumer
                this::onError,
                this::onTaskCompleted,
                repository.getRestApi().getLocationTags(accessToken), //producer
                TIMEOUT);
        onTaskStarted();

        //Location tag map types
        ConcurrencyUtils.consumeAsync(
                locationTagMap -> {
                    repository.getDatabase().locationTagMapDao().insert(locationTagMap);
                    this.onTaskCompleted();
                } , //consumer
                this::onError,
                this::onTaskCompleted,
                repository.getRestApi().getLocationTagMaps(accessToken), //producer
                TIMEOUT);
        onTaskStarted();

        //Users
        ConcurrencyUtils.consumeAsync(
                users ->{
                    repository.getDatabase().userDao().insert(users);
                    this.onTaskCompleted();
                },  //consumer
                this::onError,
                this::onTaskCompleted,
                repository.getRestApi().getUsers(accessToken), //producer
                TIMEOUT);
        onTaskStarted();


        //Patient identifier types
        ConcurrencyUtils.consumeAsync(
                patientIdentifierTypes ->{
                    repository.getDatabase().patientIdentifierTypeDao().insert(patientIdentifierTypes);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getPatientIdentifierTypes(accessToken), //producer
                TIMEOUT);
        onTaskStarted();

        //Providers
        ConcurrencyUtils.consumeAsync(
                providers ->{
                    repository.getDatabase().providerDao().insert(providers);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getProviders(accessToken), //producer
                TIMEOUT);
        onTaskStarted();

        //Concept name
        ConcurrencyUtils.consumeAsync(
                conceptNames ->{
                    repository.getDatabase().conceptNameDao().insert(conceptNames);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getConceptNames(accessToken), //producer
                TIMEOUT);
        onTaskStarted();

        //Concept answer
        ConcurrencyUtils.consumeAsync(
                conceptAnswers ->{

                    //repository.getDatabase().conceptAnswerDao().removeAll();

                    List<ConceptAnswer> ans = repository.getDatabase().conceptAnswerDao().getAll();

                    repository.getDatabase().conceptAnswerDao().insert(conceptAnswers);
                    this.onTaskCompleted();

                }, //consumer
                this::onError,
                repository.getRestApi().getConceptAnswers(accessToken), //producer
                TIMEOUT);
        onTaskStarted();

        //Encounter types
        ConcurrencyUtils.consumeAsync(
                encounterTypes ->{
                    repository.getDatabase().encounterTypeDao().insert(encounterTypes);
                    this.onTaskCompleted();

                }, //consumer
                this::onError,
                repository.getRestApi().getEncounterTypes(accessToken), //producer
                TIMEOUT);
        onTaskStarted();

        //Concept
        ConcurrencyUtils.consumeAsync(
                visitTypes ->{
                    repository.getDatabase().visitTypeDao().insert(visitTypes);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getVisitTypes(accessToken), //producer
                TIMEOUT);
        onTaskStarted();

        ConcurrencyUtils.consumeAsync(
                concepts ->{
                    repository.getDatabase().conceptDao().insert(concepts);
                    this.onTaskCompleted();

                }, //consumer
                this::onError,
                repository.getRestApi().getConcept(accessToken), //producer
                TIMEOUT);
        onTaskStarted();

        ConcurrencyUtils.consumeAsync(
                drugs ->{
                    repository.getDatabase().drugDao().insert(drugs);
                    this.onTaskCompleted();
                },//consumer
                this::onError,
                repository.getRestApi().getDrugs(accessToken),
                //producer
                TIMEOUT);
        onTaskStarted();
    }

    protected void notifySyncCompleted() {
        Intent intent = new Intent(ServiceManager.IntentAction.PULL_META_DATA_REMOTE_COMPLETE);
        intent.putExtras(mBundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onError(Throwable throwable) {
        super.onError(throwable);

        Intent intent = new Intent(ServiceManager.IntentAction.PULL_META_DATA_REMOTE_INTERRUPT);
        intent.putExtras(mBundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
