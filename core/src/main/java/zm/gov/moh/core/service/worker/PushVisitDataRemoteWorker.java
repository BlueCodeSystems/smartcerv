package zm.gov.moh.core.service.worker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.common.collect.Sets;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import androidx.annotation.NonNull;
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

        //Get all visits
        Long[] allVisits = db.visitDao().getAllVisitIDs();//.toArray(new Long[0]);
        Log.i(TAG, "Total number of visits on device:"+allVisits.length);
        Set allVisitsSet = Sets.newHashSet(allVisits);

        long[] pushedEntityId = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.VISIT.getId(), Status.SYNCED.getCode());
        Log.i(TAG, "Pushed visit entity Number = " + pushedEntityId.length);
        Set allPushedVisitsSet = Sets.newHashSet(pushedEntityId);

        allVisitsSet.removeAll(allPushedVisitsSet);
        Log.i(TAG, "Unpushed visit IDS number " + allVisitsSet.size());

        Long[] allVisitsArray = new Long[allVisitsSet.size()];
        allVisitsSet.toArray(allVisitsArray);

        int step = 500;

        //int pushedSize = pushedEntityId.length;
        //final long offset = Constant.LOCAL_ENTITY_ID_OFFSET;
        //Long[] unpushedVisitEntityId = db.visitDao().findEntityNotWithId(offset, pushedEntityId);
        //Log.i(TAG, "unpushed visit entity Number = " + unpushedVisitEntityId.length);

        if(allVisitsArray.length <= step) {
            Long[] copiedAllVisitsArray = new Long[500];
            int i;
            for (i = 0; i < 500; i += step) {
                copiedAllVisitsArray[i] = allVisitsArray[i];
            }
            //List<Visit> patientVisits = (List<Visit>) createVisits().iterator();
            List<Visit> patientVisits = createVisits((copiedAllVisitsArray));
            //List<Visit> patientVisits = new ArrayList<>();
            Log.i(TAG, "Number of Patient visits from create visits = " + patientVisits.size());
            Log.i(TAG, "Patient visits from create visits = " + patientVisits);


            if (patientVisits.size() - i > step)
                Log.i(TAG, "Visit Size is being retrieved");
            /*LOGGER.log( Level.FINE, "processing {0} entries in loop", patientVisits.size() );*/
            restApi.putVisit(accessToken, batchVersion, patientVisits.toArray(new Visit[patientVisits.size()]))
                    .timeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    .subscribe(onComplete(allVisitsArray, EntityType.VISIT.getId()), this::onError);

        }

        if(mResult.equals(Result.success()))
            onTaskCompleted();

        /*LOGGER.log(Level.FINEST, "Visit Data Pushed Successfully");*/
        return this.mResult;
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
        Log.i(TAG, "Number of visit entities = " + visitEntities.size());

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