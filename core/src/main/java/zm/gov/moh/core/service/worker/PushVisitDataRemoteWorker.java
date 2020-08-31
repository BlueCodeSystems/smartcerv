package zm.gov.moh.core.service.worker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import zm.gov.moh.core.Constant;
import zm.gov.moh.core.model.Encounter;
import zm.gov.moh.core.model.IntentAction;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.Obs;
import zm.gov.moh.core.model.Patient;
import zm.gov.moh.core.model.PatientIdentifier;
import zm.gov.moh.core.model.PersonAttribute;
import zm.gov.moh.core.model.Response;
import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.repository.database.entity.derived.PersonIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.domain.ObsEntity;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.ServiceManager;

public class PushVisitDataRemoteWorker extends RemoteWorker {
    public static final String TAG = "PushVisit";

    public PushVisitDataRemoteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams){
        super(context, workerParams);
    }

    @Override
    @NonNull
    public Result doWork() {

        long batchVersion = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        long[] unpushedEntityId = db.entityMetadataDao().findEntityIdByTypeNoRemoteStatus(EntityType.VISIT.getId(), Status.PUSHED.getCode());
        //long[] unpushedVisitEntityId = db.entityMetadataDao().findEntityIdByTypeNoRemoteStatus(EntityType.VISIT.getId(), Status.NOT_PUSHED.getCode());//This gets the Entity IDs that are not Synced.
        Log.i(TAG, "Unpushed visits" + unpushedEntityId.length);
        Log.i(TAG, "Unpushed visits entity ID" + unpushedEntityId.equals(unpushedEntityId));
        Log.i(TAG, "Unpushed visits bundle" + unpushedEntityId.equals(mBundle.describeContents()));
        Log.i(TAG, String.format("Unpushed visits%s", Arrays.toString(unpushedEntityId))); //unpushedEntityId: {12429, 12430, 12431, 12432, 12425, 12426, 12427, 12428, 12490, 12491, + 890 more}
        //final long offset = Constant.LOCAL_ENTITY_ID_OFFSET;
        //final long offset = 0;
        final long offset = Constant.LOCAL_ENTITY_ID_OFFSET;
        //Long[] unpushedVisitEntityId = db.visitDao().findEntityNotWithId(offset, pushedEntityId);
        Long[] unpushedVisitEntityId = db.visitDao().findEntityWithId(offset, unpushedEntityId); // offset : 0 unpushedEntityId: {12429, 12430, 12431, 12432, 12425, 12426, 12427, 12428, 12490, 12491, + 890 more}
        Log.i(TAG, "Unpushed visits" + unpushedVisitEntityId.length);
        Log.i(TAG, String.format("Unpushed Entity ID visits%s", Arrays.toString(unpushedVisitEntityId)));
        Log.i(TAG, "Unpushed visits length" + unpushedVisitEntityId.length);
        Log.i(TAG, "Unpushed visits" + unpushedVisitEntityId.equals(unpushedVisitEntityId));
        Log.i(TAG, "Unpushed visits bundle" + unpushedVisitEntityId.equals(mBundle.describeContents()));
        Log.i(TAG, String.format("Unpushed visits%s", Arrays.toString(unpushedEntityId)));



        if(unpushedVisitEntityId.length > 0) {


            List<Visit> patientVisits = createVisits(unpushedVisitEntityId);

            if(patientVisits.size() > 0)
                restApi.putVisit(accessToken, batchVersion, patientVisits.toArray(new Visit[patientVisits.size()]))
                        .timeout(TIMEOUT, TimeUnit.MILLISECONDS)
                        .subscribe(onComplete(unpushedVisitEntityId, EntityType.VISIT.getId()), this::onError);

        }

        if(mResult.equals(Result.success()))
            onTaskCompleted();
        return this.mResult;
    }

    public void onTaskCompleted(){

        repository.getDefaultSharePrefrences().edit().putString(Key.LAST_DATA_SYNC_DATETIME,LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).apply();
        mLocalBroadcastManager.sendBroadcast(new Intent(IntentAction.REMOTE_SYNC_COMPLETE));
    }

    public Consumer<Response[]> onComplete(Long[] entityIds, int entityTypeId){

        return param -> {

            for(Long entityId:entityIds) {
                db.entityMetadataDao().updateSyncStatus(entityId, Status.PUSHED.getCode());
                /* TODO
                /*TODO Find all entities that have Status NOT SYNCED(entites of same ID) & Delete them..*/
            }
        };
    }

    public List<Visit> createVisits (Long ...visitEntityId){

        List<VisitEntity> visitEntities = db.visitDao().getById(visitEntityId); //visitEntities: size = 10 visitEntityId: Long[900]

        if(visitEntities.size() > 0) { //visitEntities: size = 10

            int visitIndex = 0;
            List<Visit> visits = new ArrayList<>();

            List<Long> visitIds = Observable.fromIterable(visitEntities).map(visitEntity -> visitEntity.getVisitId())
                    .toList().blockingGet();

            List<EncounterEntity> encounterEntities = db.encounterDao().getByVisitId(visitIds);
            Log.i(TAG, "Encounter Entities containing encounter Ids" + encounterEntities.contains(visitIds));
            Log.i(TAG, "Encounter Entities List size" + encounterEntities.size());
            Log.i(TAG, "Encounter Entities List size" + encounterEntities.equals(visitIds.addAll(visitIds)));
            Log.i(TAG, "Encounter Entities List size" + encounterEntities.equals(mBundle.describeContents()));
            Log.i(TAG, String.format("Entity encounters%s", Arrays.toString(new List[]{visitIds})));
            Log.i(TAG, "Encounter Entities List size" + encounterEntities.containsAll(visitIds));



            List<Long> encounterIds = Observable.fromIterable(encounterEntities)
                    .map(encounterEntity -> encounterEntity.getEncounterId())
                    .toList()
                    .blockingGet();

            List<ObsEntity> obsEntities = db.obsDao().getObsByEncounterId(encounterIds);
            Log.i(TAG, "Obs Entities containing encounter Ids" + obsEntities.contains(encounterIds));
            Log.i(TAG, "Obs Entities List size" + obsEntities.size());
            Log.i(TAG, "Obs Entities List size" + obsEntities.equals(encounterIds));
            Log.i(TAG, "Obs Entities List size" + obsEntities.equals(mBundle.describeContents()));
            Log.i(TAG, String.format("Obs Entities%s", Arrays.toString(new List[]{encounterIds})));
            Log.i(TAG, "Obs Entities List size" + obsEntities.toArray());




            for (VisitEntity visitEntity : visitEntities) {

                try {

                    Visit.Builder visit = normalizeVisit(visitEntity);

                    List<EncounterEntity> visitEncounter = Observable.fromIterable(encounterEntities)
                            .filter(encounterEntity -> (encounterEntity.getVisitId().longValue() == visitEntity.getVisitId().longValue())).toList().blockingGet();


                    Encounter[] encounters = new Encounter[visitEncounter.size()];
                    int index = 0;

                    for (EncounterEntity encounterEntity : visitEncounter) {

                        if (encounterEntity == null)
                            continue;
                        Encounter encounter = normalizeEncounter(encounterEntity);


                        List<ObsEntity> encounterObs = Observable.fromIterable(obsEntities)
                                .filter(obsEntity -> (obsEntity.getEncounterId() == encounterEntity.getEncounterId()))
                                .toList()
                                .blockingGet();

                        Obs[] obs = Observable.fromIterable(encounterObs)
                                .map((this::normalizeObs))
                                .toList()
                                .blockingGet()
                                .toArray(new Obs[encounterObs.size()]);


                        encounter.setObs(obs);

                        encounters[index++] = encounter;
                    }

                    visits.add(visit.setEncounters(encounters).build());
                }
                catch (Exception e){

                }
            }

            return visits;
        }
        return null;
    }

    public Obs normalizeObs(ObsEntity obsEntity){

        Obs obs = new Obs();
        String concept = db.conceptDao().getConceptUuidById(obsEntity.getConceptId());
        String person = db.personIdentifierDao().getUuidByPersonId(obsEntity.getPersonId());

        if(person == null)
            person = db.patientIdentifierDao().getRemotePatientUuid(obsEntity.getPersonId());//db.patientIdentifierDao().getRemotePatientUuid(obsEntity.getPersonId(), 3);


        obs.setConcept(concept);
        obs.setObsDatetime(obsEntity.getObsDateTime());
        obs.setPerson(person);

        if(obsEntity.getValueCoded() != null )
            obs.setValue(db.conceptDao().getConceptUuidById(obsEntity.getValueCoded()));
        else if (obsEntity.getValueDateTime() != null )
            obs.setValue(obsEntity.getValueDateTime().toString());
        else if (obsEntity.getValueNumeric() != null)
            obs.setValue(obsEntity.getValueNumeric().toString());
        else if (obsEntity.getValueText() != null)
            obs.setValue(obsEntity.getValueText());

        return obs;
    }

    public Visit.Builder normalizeVisit(VisitEntity visitEntity) throws Exception {

        Visit.Builder visit = new  Visit.Builder();
        String visitType = db.visitTypeDao().getUuidVisitTypeById(visitEntity.getVisitTypeId());
        String patient = db.personIdentifierDao().getUuidByPersonId(visitEntity.getPatientId());//db.patientIdentifierDao().getRemotePatientUuid(visitEntity.getPatientId(),3);
        String location = db.locationDao().getUuidById(visitEntity.getLocationId());

        List<PersonIdentifier> personIdentifiers = db.personIdentifierDao().getAll();
        if(patient == null || visitType == null || location == null)
            throw new Exception("Inadequate parameters");


        visit.setVisitType(visitType)
                .setPatient(patient)
                .setLocation(location)
                .setStartDatetime(visitEntity.getDateStarted())
                .setStopDatetime(visitEntity.getDateStopped());

        return visit;
    }

    public Encounter normalizeEncounter(EncounterEntity encounterEntity){

        Encounter encounter = new Encounter();

        String location = db.locationDao().getUuidById(encounterEntity.getLocationId());
        String encounterType = db.encounterTypeDao().getUuidById(encounterEntity.getEncounterType());
        Long providerID=db.encounterProviderDao().getProviderByEncounterId(encounterEntity.getEncounterId());
        String providerUuid=db.providerDao().getProviderUuidByProviderId(providerID);
        encounter.setProvider(providerUuid);
        encounter.setLocation(location);
        encounter.setEncounterType(encounterType);
        encounter.setEncounterDatetime(encounterEntity.getEncounterDatetime());

        return encounter;
    }

}
