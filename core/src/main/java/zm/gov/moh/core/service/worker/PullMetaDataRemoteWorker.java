package zm.gov.moh.core.service.worker;

import android.content.Context;
import android.content.Intent;

import org.threeten.bp.LocalDateTime;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.WorkerParameters;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.domain.Concept;
import zm.gov.moh.core.repository.database.entity.domain.ConceptAnswer;
import zm.gov.moh.core.repository.database.entity.domain.ConceptName;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.RemoteService;
import zm.gov.moh.core.service.ServiceManager;
import zm.gov.moh.core.utils.ConcurrencyUtils;


public class PullMetaDataRemoteWorker extends RemoteWorker {

    public PullMetaDataRemoteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams){
        super(context, workerParams);
    }


    @Override
    @NonNull
    public Result doWork() {

        String lastSyncDate = repository.getDefaultSharePrefrences().getString(Key.LAST_SYNC_DATE,null);

        if(lastSyncDate != null)
            MIN_DATETIME = LocalDateTime.parse(lastSyncDate);
        //Get from rest API and insert into database asynchronously


        getConcept(accessToken, MIN_DATETIME, OFFSET,LIMIT);

        getConceptName(accessToken, MIN_DATETIME, OFFSET,LIMIT);

        getConceptAnswer(accessToken, MIN_DATETIME,OFFSET, LIMIT);

        getLocations(accessToken, MIN_DATETIME, OFFSET, LIMIT);

        //Location attributes
        ConcurrencyUtils.consumeBlocking(
                locationAttributes -> {
                    repository.getDatabase().locationAttributeDao().insert(locationAttributes);
                }, //consumer
                this::onError,
                repository.getRestApi().getLocationAttributes(accessToken), //producer
                TIMEOUT);

        //Location attributes types
        ConcurrencyUtils.consumeBlocking(
                locationAttributeTypes -> {
                    repository.getDatabase().locationAttributeTypeDao().insert(locationAttributeTypes);
                } , //consumer
                this::onError,
                repository.getRestApi().getLocationAttributeTypes(accessToken), //producer
                TIMEOUT);

        //Location tag types
        ConcurrencyUtils.consumeBlocking(
                locationTag -> {
                    repository.getDatabase().locationTagDao().insert(locationTag);
                } , //consumer
                this::onError,
                repository.getRestApi().getLocationTags(accessToken), //producer
                TIMEOUT);

        //Location tag map types
        ConcurrencyUtils.consumeBlocking(
                locationTagMap -> {
                    repository.getDatabase().locationTagMapDao().insert(locationTagMap);
                } , //consumer
                this::onError,
                repository.getRestApi().getLocationTagMaps(accessToken), //producer
                TIMEOUT);

        //Users
        ConcurrencyUtils.consumeBlocking(
                users ->{
                    repository.getDatabase().userDao().insert(users);
                },  //consumer
                this::onError,
                repository.getRestApi().getUsers(accessToken), //producer
                TIMEOUT);


        //Patient identifier types
        ConcurrencyUtils.consumeBlocking(
                patientIdentifierTypes ->{
                    repository.getDatabase().patientIdentifierTypeDao().insert(patientIdentifierTypes);
                }, //consumer
                this::onError,
                repository.getRestApi().getPatientIdentifierTypes(accessToken), //producer
                TIMEOUT);

        //Providers
        ConcurrencyUtils.consumeBlocking(
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
        ConcurrencyUtils.consumeBlocking(
                visitTypes ->{
                    repository.getDatabase().visitTypeDao().insert(visitTypes);
                    this.onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getVisitTypes(accessToken), //producer
                TIMEOUT);

        // Drugs
        ConcurrencyUtils.consumeBlocking(
                drugs ->{
                    repository.getDatabase().drugDao().insert(drugs);
                    this.onTaskCompleted();
                },//consumer
                this::onError,
                repository.getRestApi().getDrugs(accessToken),
                //producer
                TIMEOUT);

        // Provider Attributes
        ConcurrencyUtils.consumeBlocking(
                attributes ->{
                    repository.getDatabase().providerAttributeDao().insert(attributes);
                    this.onTaskCompleted();
                },//consumer
                this::onError,
                repository.getRestApi().getProviderAttribute(accessToken),
                //producer
                TIMEOUT);

        // Provider Attribute Types
        ConcurrencyUtils.consumeBlocking(
                attributes ->{
                    repository.getDatabase().providerAttributeTypeDao().insert(attributes);
                    this.onTaskCompleted();
                },//consumer
                this::onError,
                repository.getRestApi().getProviderAttributeType(accessToken),
                //producer
                TIMEOUT);

        return this.mResult;
    }

    //Concept name
    public void getConceptName(String accessToken, LocalDateTime MIN_DATETIME,final long offset, int limit){

        ConcurrencyUtils.consumeBlocking(

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

        ConcurrencyUtils.consumeBlocking(

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

        ConcurrencyUtils.consumeBlocking(

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

        ConcurrencyUtils.consumeBlocking(

                locations -> {

                    if(locations.length > 0){

                        db.locationDao().insert(locations);
                        updateMetadata(locations, EntityType.LOCATION);
                        getLocations(accessToken,MIN_DATETIME,offset + limit,limit);
                    }else {
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

            mBundle.putSerializable(Key.ENTITY_TYPE, EntityType.PATIENT);
            ServiceManager.getInstance(getApplicationContext())
                    .setService(ServiceManager.Service.PUSH_ENTITY_REMOTE)
                    .putExtras(mBundle)
                    .start();
        }

    }

}
