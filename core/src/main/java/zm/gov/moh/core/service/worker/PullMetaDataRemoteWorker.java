package zm.gov.moh.core.service.worker;

import android.content.Context;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.utils.ConcurrencyUtils;


public class PullMetaDataRemoteWorker extends RemoteWorker {

    public PullMetaDataRemoteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams){
        super(context, workerParams);
    }


    @Override
    @NonNull
    public Result doWork() {

        taskPoolSize = 14;

        if(lastMetadataSyncDate != null)
            MIN_DATETIME = LocalDateTime.parse(lastMetadataSyncDate);
        //Get from rest API and insert into database asynchronously


        getConcept(accessToken, MIN_DATETIME, OFFSET,LIMIT);

        getConceptName(accessToken, MIN_DATETIME, OFFSET,LIMIT);

        getConceptAnswer(accessToken, MIN_DATETIME,OFFSET, LIMIT);

        getLocations(accessToken, MIN_DATETIME, OFFSET, LIMIT);

        //Location attributes
        ConcurrencyUtils.consumeAsync(
                locationAttributes -> {
                    repository.getDatabase().locationAttributeDao().insert(locationAttributes);
                    onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getLocationAttributes(accessToken), //producer
                TIMEOUT);

        //Location attributes types
        ConcurrencyUtils.consumeAsync(
                locationAttributeTypes -> {
                    repository.getDatabase().locationAttributeTypeDao().insert(locationAttributeTypes);
                    onTaskCompleted();
                } , //consumer
                this::onError,
                repository.getRestApi().getLocationAttributeTypes(accessToken), //producer
                TIMEOUT);

        //Location tag types
        ConcurrencyUtils.consumeAsync(
                locationTag -> {
                    repository.getDatabase().locationTagDao().insert(locationTag);
                    onTaskCompleted();
                } , //consumer
                this::onError,
                repository.getRestApi().getLocationTags(accessToken), //producer
                TIMEOUT);

        //Location tag map types
        ConcurrencyUtils.consumeAsync(
                locationTagMap -> {
                    repository.getDatabase().locationTagMapDao().insert(locationTagMap);
                    onTaskCompleted();
                } , //consumer
                this::onError,
                repository.getRestApi().getLocationTagMaps(accessToken), //producer
                TIMEOUT);


        //Patient identifier types
        ConcurrencyUtils.consumeAsync(
                patientIdentifierTypes ->{
                    repository.getDatabase().patientIdentifierTypeDao().insert(patientIdentifierTypes);
                    onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getPatientIdentifierTypes(accessToken), //producer
                TIMEOUT);

        //Encounter types
        ConcurrencyUtils.consumeAsync(
                encounterTypes ->{
                    repository.getDatabase().encounterTypeDao().insert(encounterTypes);
                    onTaskCompleted();

                }, //consumer
                this::onError,
                repository.getRestApi().getEncounterTypes(accessToken), //producer
                TIMEOUT);

        //Visit types
        ConcurrencyUtils.consumeAsync(
                visitTypes ->{
                    repository.getDatabase().visitTypeDao().insert(visitTypes);
                    onTaskCompleted();
                }, //consumer
                this::onError,
                repository.getRestApi().getVisitTypes(accessToken), //producer
                TIMEOUT);

        // Drugs
        ConcurrencyUtils.consumeAsync(
                drugs ->{
                    repository.getDatabase().drugDao().insert(drugs);
                    onTaskCompleted();
                },//consumer
                this::onError,
                repository.getRestApi().getDrugs(accessToken),
                //producer
                TIMEOUT);

        // Provider Attributes
        ConcurrencyUtils.consumeAsync(
                attributes ->{
                    repository.getDatabase().providerAttributeDao().insert(attributes);
                    onTaskCompleted();
                },//consumer
                this::onError,
                repository.getRestApi().getProviderAttribute(accessToken),
                //producer
                TIMEOUT);

        // Provider Attribute Types
        ConcurrencyUtils.consumeAsync(
                attributes ->{
                    repository.getDatabase().providerAttributeTypeDao().insert(attributes);
                    onTaskCompleted();
                },//consumer
                this::onError,
                repository.getRestApi().getProviderAttributeType(accessToken),
                //producer
                TIMEOUT);

       if(awaitResult().equals(Result.success())){

           repository.getDefaultSharePrefrences().edit()
                   .putString(Key.LAST_METADATA_SYNC_DATETIME, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                   .apply();
       }

       return awaitResult();

    }

    //Concept name
    public void getConceptName(String accessToken, LocalDateTime MIN_DATETIME,final long offset, int limit){

        ConcurrencyUtils.consumeAsync(

                conceptNames -> {

                    if(conceptNames.length > 0){

                        db.conceptNameDao().insert(conceptNames);
                        updateMetadata(conceptNames, EntityType.CONCEPT_NAME);
                        getConceptName(accessToken,MIN_DATETIME,offset + limit,limit);
                    }else
                        onTaskCompleted();


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
                    }else
                        onTaskCompleted();

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
                    }else
                        this.onTaskCompleted();


                }, //consumer
                this::onError,
                repository.getRestApi().getConcept(accessToken,MIN_DATETIME,offset,limit), //producer
                TIMEOUT);
    }

    //Location
    public void getLocations(String accessToken, LocalDateTime MIN_DATETIME,final long offset, int limit){

        ConcurrencyUtils.consumeAsync(

                locations -> {

                    if(locations.length > 0){

                        db.locationDao().insert(locations);
                        updateMetadata(locations, EntityType.LOCATION);
                        getLocations(accessToken,MIN_DATETIME,offset + limit,limit);
                    }else
                        onTaskCompleted();

                }, //consumer
                this::onError,
                repository.getRestApi().getLocations(accessToken,MIN_DATETIME,offset,limit), //producer
                TIMEOUT);
    }

}
