/*package zm.gov.moh.core.service.worker;

*/import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
/*import zm.gov.moh.core.Constant;
import zm.gov.moh.core.model.Encounter;
import zm.gov.moh.core.model.IntentAction;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.Obs;
import zm.gov.moh.core.model.Response;
import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.repository.database.entity.derived.PersonIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.domain.ObsEntity;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;

public class PushVisitRemoteWorkerTwo extends RemoteWorker {
    public static final String TAG = "PushVisit";

    public PushVisitRemoteWorkerTwo(@NonNull Context context, @NonNull WorkerParameters workerParams){
        super(context, workerParams);
    }

    @Override
    @NonNull
    public Result doWork() {
        long batchVersion = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        long[] pushedEntityId = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.VISIT.getId(), Status.PUSHED.getCode());
        long[] pushedVisitEntityId = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.VISIT.getId(), RemoteWorker.Status.PUSHED.getCode());
        Log.i(TAG, "Total number of visits on tablet:"+pushedEntityId.length);

        int pushedSize = pushedEntityId.length;
        Log.i(TAG, String.format("Pushed Size Number = " + pushedSize));
        final long offset = Constant.LOCAL_ENTITY_ID_OFFSET;
        Long[] notPushedVisitEntityId = db.visitDao().findEntityNotWithId(offset, pushedVisitEntityId);
        //Long[] unpushedVisitEntityId = db.visitDao().findEntityNotWithId(offset, pushedEntityId);
        Long[] unpushedVisitEntityId = db.visitDao().findEntityNotWithId(offset, EntityType.VISIT.getId(), Status.PUSHED.getCode());
        Log.i(TAG, "Total number of unpushed visits on tablet:"+notPushedVisitEntityId.length);
        final int SQL_MAX_VARS = 999;
        Long[] notPushedId;
        //notPushedVisitEntityId = notPushedVisitEntityId;


        if(EntityType.VISIT.getId() > 100) {
            for (int i = 0; i < 100; i++) {
                //notPushedId[i] = EntityType.VISIT.getId()
                //entitiesForVisit.get(entityIndex).getId());
                //notPushedId.
            }
            // Load the remaining Ids (the last section that's not divisible by SQL_MAX_VARS)
            addAll(createVisits(notPushedVisitEntityId)
            );

            Log.i(TAG, "Number of unpushed Patient visits from create visits = " + notPushedVisitEntityId.length);
            Log.i(TAG, "Size of unpushed Patient visits from create visits = " + mBundle.size());



            List<Visit> patientVisits = createVisits(notPushedVisitEntityId);
            Log.i(TAG, "Number of Patient visits from create visits = " + patientVisits.size());
            Log.i(TAG, "Size of Patient visits from create visits = " + patientVisits);

            if(patientVisits.size() > 0)
                Log.i(TAG, "Visit Size is being retrieved");
            Log.i(TAG, "Number of Patient visits from create visits = " + patientVisits.size());
            Log.i(TAG, "Size of Patient visits from create visits = " + patientVisits);
            restApi.putVisit(accessToken, batchVersion, patientVisits.toArray(new Visit[patientVisits.size()]))
                    .timeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    .subscribe(onComplete(notPushedVisitEntityId, EntityType.VISIT.getId()), this::onError);

        }

        if(unpushedVisitEntityId.length > 0) {
            Log.i(TAG, "Number of unpushed Patient visits from create visits = " + unpushedVisitEntityId.length);
            Log.i(TAG, "Size of unpushed Patient visits from create visits = " + mBundle.size());



            List<Visit> patientVisits = createVisits(unpushedVisitEntityId);
            Log.i(TAG, "Number of Patient visits from create visits = " + patientVisits.size());
            Log.i(TAG, "Size of Patient visits from create visits = " + patientVisits);

            if(patientVisits.size() > 0)
                Log.i(TAG, "Visit Size is being retrieved");
            Log.i(TAG, "Number of Patient visits from create visits = " + patientVisits.size());
            Log.i(TAG, "Size of Patient visits from create visits = " + patientVisits);
            restApi.putVisit(accessToken, batchVersion, patientVisits.toArray(new Visit[patientVisits.size()]))
                    .timeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    .subscribe(onComplete(unpushedVisitEntityId, EntityType.VISIT.getId()), this::onError);

        }

        if(mResult.equals(Result.success()))
            onTaskCompleted();
        return this.mResult;
    }

    private PushVisitRemoteWorkerTwo addAll(List<Visit> visits) {
        return this;
    }

    public void onTaskCompleted(){

        repository.getDefaultSharePrefrences().edit().putString(Key.LAST_DATA_SYNC_DATETIME,LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).apply();
        mLocalBroadcastManager.sendBroadcast(new Intent(IntentAction.REMOTE_SYNC_COMPLETE));
    }

    public Consumer<Response[]> onComplete(Long[] entityIds, int entityTypeId){

        return param -> {

            for(Long entityId:entityIds) {

                EntityMetadata entityMetadata = new EntityMetadata(entityId,entityTypeId, Status.PUSHED.getCode());
                db.entityMetadataDao().insert(entityMetadata);
            }
        };
    }

    public List<Visit> createVisits (Long ...visitEntityId){

        List<VisitEntity> visitEntities = db.visitDao().getById(visitEntityId);
        Log.i(TAG, "Size of visit entities = " + visitEntities.size());
        Log.i(TAG, "Number of visit entities = " + visitEntities);

        if(visitEntities.size() > 0) {


            int visitIndex = 0;
            List<Visit> visits = new ArrayList<>();

            List<Long> visitIds = Observable.fromIterable(visitEntities).map(visitEntity -> visitEntity.getVisitId())
                    .toList().blockingGet();

            List<EncounterEntity> encounterEntities = db.encounterDao().getByVisitId(visitIds);

            List<Long> encounterIds = Observable.fromIterable(encounterEntities)
                    .map(encounterEntity -> encounterEntity.getEncounterId())
                    .toList()
                    .blockingGet();

            List<ObsEntity> obsEntities = db.obsDao().getObsByEncounterId(encounterIds);



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
*/