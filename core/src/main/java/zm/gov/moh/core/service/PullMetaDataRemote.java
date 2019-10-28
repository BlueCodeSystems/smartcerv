package zm.gov.moh.core.service;

import android.content.Intent;

import org.threeten.bp.LocalDateTime;

import java.util.List;

import androidx.annotation.Nullable;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.domain.Concept;
import zm.gov.moh.core.repository.database.entity.domain.ConceptAnswer;
import zm.gov.moh.core.repository.database.entity.domain.ConceptName;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.utils.ConcurrencyUtils;

@Deprecated
public class PullMetaDataRemote extends RemoteService {

    int tasksStarted = 16;
    int tasksCompleted = 0;
    public PullMetaDataRemote(){
        super(ServiceManager.Service.PULL_META_DATA_REMOTE);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        super.onHandleIntent(intent);
    }

    @Override
    protected void executeAsync() {

        List<Person> people = db.personDao().getAllT();

        String lastSyncDate = repository.getDefaultSharePrefrences().getString(Key.LAST_SYNC_DATE,null);

        if(lastSyncDate != null)
            MIN_DATETIME = LocalDateTime.parse(lastSyncDate);
        //Get from rest API and insert into database asynchronously


        getConcept(accessToken, MIN_DATETIME, OFFSET,LIMIT);

        getConceptName(accessToken, MIN_DATETIME, OFFSET,LIMIT);

        getConceptAnswer(accessToken, MIN_DATETIME,OFFSET, LIMIT);

        getLocations(accessToken, MIN_DATETIME, OFFSET, LIMIT);

        //Location attributes
        ConcurrencyUtils.consumeAsync(
                locationAttributes -> {
                    repository.getDatabase().locationAttributeDao().insert(locationAttributes);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getLocationAttributes(accessToken), //producer
                TIMEOUT);

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


        //Patient identifier types
        ConcurrencyUtils.consumeAsync(
                patientIdentifierTypes ->{
                    repository.getDatabase().patientIdentifierTypeDao().insert(patientIdentifierTypes);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getPatientIdentifierTypes(accessToken), //producer
                TIMEOUT);

        //Providers
        ConcurrencyUtils.consumeAsync(
                providers ->{
                    repository.getDatabase().providerDao().insert(providers);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getProviders(accessToken), //producer
                TIMEOUT);


        //Encounter types
        ConcurrencyUtils.consumeAsync(
                encounterTypes ->{
                    repository.getDatabase().encounterTypeDao().insert(encounterTypes);
                    this.onTaskCompleted();

                }, //consumer
                this::onError,
                repository.getRestApi().getEncounterTypes(accessToken), //producer
                TIMEOUT);

        //Concept
        ConcurrencyUtils.consumeAsync(
                visitTypes ->{
                    repository.getDatabase().visitTypeDao().insert(visitTypes);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getVisitTypes(accessToken), //producer
                TIMEOUT);

        // Drugs
        ConcurrencyUtils.consumeAsync(
                drugs ->{
                    repository.getDatabase().drugDao().insert(drugs);
                    this.onTaskCompleted();
                },//consumer
                this::onError,
                repository.getRestApi().getDrugs(accessToken),
                //producer
                TIMEOUT);

        // Provider Attributes
        ConcurrencyUtils.consumeAsync(
                attributes ->{
                    repository.getDatabase().providerAttributeDao().insert(attributes);
                    this.onTaskCompleted();
                },//consumer
                this::onError,
                repository.getRestApi().getProviderAttribute(accessToken),
                //producer
                TIMEOUT);

        // Provider Attribute Types
        ConcurrencyUtils.consumeAsync(
                attributes ->{
                    repository.getDatabase().providerAttributeTypeDao().insert(attributes);
                    this.onTaskCompleted();
                },//consumer
                this::onError,
                repository.getRestApi().getProviderAttributeType(accessToken),
                //producer
                TIMEOUT);
    }

    //Concept name
    public void getConceptName(String accessToken, LocalDateTime MIN_DATETIME,final long offset, int limit){

        ConcurrencyUtils.consumeAsync(

                conceptNames -> {

                    if(conceptNames.length > 0){

                        db.conceptNameDao().insert(conceptNames);
                        updateMetadata(conceptNames, EntityType.CONCEPT_NAME);
                        getConceptName(accessToken,MIN_DATETIME,offset + limit,limit);
                    }else {

                       List<ConceptName> conceptNameList = db.conceptNameDao().getAll();
                        conceptNameList.size();
                        this.onTaskCompleted();

                    }

                }, //consumer
                this::onError,
                repository.getRestApi().getConceptNames(accessToken,MIN_DATETIME,offset,limit), //producer
                TIMEOUT);
    }

    //Concept answer
    public void getConceptAnswer(String accessToken, LocalDateTime MIN_DATETIME,final long offset, int limit){

        ConcurrencyUtils.consumeAsync(

                conceptAnswers -> {

                    if(conceptAnswers.length > 0){

                        db.conceptAnswerDao().insert(conceptAnswers);
                        updateMetadata(conceptAnswers, EntityType.CONCEPT_ANSWER);
                        getConceptAnswer(accessToken,MIN_DATETIME,offset + limit,limit);
                    }else {
                        this.onTaskCompleted();
                       List<ConceptAnswer> conceptAnswerList = db.conceptAnswerDao().getAll();
                       conceptAnswerList.size();
                    }

                }, //consumer
                this::onError,
                repository.getRestApi().getConceptAnswers(accessToken,MIN_DATETIME,offset,limit), //producer
                TIMEOUT);
    }

    //Concept
    public void getConcept(String accessToken, LocalDateTime MIN_DATETIME,final long offset, int limit){

        ConcurrencyUtils.consumeAsync(

                concepts -> {

                    if(concepts.length > 0){

                        db.conceptDao().insert(concepts);
                        updateMetadata(concepts, EntityType.CONCEPT);

                        getConcept(accessToken,MIN_DATETIME,offset + limit,limit);
                    }else {
                        this.onTaskCompleted();
                        List<Concept> conceptList  = db.conceptDao().getAll();
                        conceptList.size();
                    }

                }, //consumer
                this::onError,
                repository.getRestApi().getConcept(accessToken,MIN_DATETIME,offset,limit), //producer
                TIMEOUT);
    }

    //Location
    public void getLocations(String accessToken, LocalDateTime MIN_DATETIME,final long offset, int limit){

        onTaskStarted();
        ConcurrencyUtils.consumeAsync(

                locations -> {

                    if(locations.length > 0){

                        db.locationDao().insert(locations);
                        updateMetadata(locations, EntityType.LOCATION);
                        getLocations(accessToken,MIN_DATETIME,offset + limit,limit);
                    }else {
                        this.onTaskCompleted();
                        List<Location> locationList =  db.locationDao().getAllT();
                        locationList.size();
                    }

                }, //consumer
                this::onError,
                repository.getRestApi().getLocations(accessToken,MIN_DATETIME,offset,limit), //producer
                TIMEOUT);
    }

    public void onTaskCompleted(){

        tasksCompleted++;

        if(tasksCompleted == tasksStarted){
            notifyCompleted();

            mBundle.putSerializable(Key.ENTITY_TYPE, EntityType.PATIENT);
            ServiceManager.getInstance(getApplicationContext())
                    .setService(ServiceManager.Service.PUSH_ENTITY_REMOTE)
                    .putExtras(mBundle)
                    .start();
        }

    }

}
