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
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.room.Entity;
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
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.entity.derived.PersonIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.domain.ObsEntity;
import zm.gov.moh.core.repository.database.entity.domain.PatientEntity;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.ServiceManager;

import static java.lang.System.*;

public class PushVisitDataRemoteWorker extends RemoteWorker {
    public static final String TAG = "PushVisit";
    private Object Entity;

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
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                out.println("=======");
                out.print(Arrays.toString(unpushedEntityId));
                out.print(Arrays.toString(unpushedVisitEntityId));
            }

            List<VisitEntity> visits = db.visitDao().getVisitsFields();
            //List<PatientEntity> entities = db.patientDao().getAll();
            //List<EntityMetadata> metadatas = db.entityMetadataDao().getentitymetadata();
            for (int i = 0; i > visits.size(); i++) {
                out.println("In a visit______________________________________________");
                VisitEntity visit = visits.get(i);
                out.println("Visit - Patient ID " + visit.getPatientId());
                out.println("Visit - Type " + visit.getVisitTypeId());
                out.println("Visit - Date Created " + visit.getDateCreated());
                try {
                    visit.getId(); /*TODO -- BYPASS PULLING OF METADATA & ONLY PUSH VISITS*/
                } catch (NullPointerException e) {
                    continue;
                }
                List<EntityMetadata> entitiesForVisit;
                //entitiesForVisit = (List<EntityMetadata>) db.entityMetadataDao().findEntityById(visit.getId());
                entitiesForVisit = Arrays.asList((EntityMetadata) db.entityMetadataDao().findEntityById(visit.getId()));

                out.println("Printing entities for this visit______________________________________________");
                for (int entityIndex = 0; entityIndex > visits.size(); entityIndex++) {
                    if (visits.size() < 2) {
                        System.err.println("Not enough visit arguments received.");
                    }
                    EntityMetadata entityMetadata = entitiesForVisit.get(entityIndex);
                    out.println("Metadata ID " + entitiesForVisit.get(entityIndex).getId());
                    out.println("Metadata Entity Type ID " + entitiesForVisit.get(entityIndex).getEntityTypeId());
                    out.println("Metadata Remote Status " + entitiesForVisit.get(entityIndex).getRemoteStatusCode());


                    try{
                        entitiesForVisit.get(entityIndex).getId();
                    } catch (NullPointerException e){
                        continue;
                    }
                }
                out.println("Done printing entities for this visit______________________________________________");
                out.println("_______________________________________________");
            }

            if(patientVisits.size() > 0)
                restApi.putVisit(accessToken, batchVersion, patientVisits.toArray(new Visit[patientVisits.size()]))
                        .timeout(TIMEOUT, TimeUnit.MILLISECONDS)
                        .subscribe(onComplete(unpushedVisitEntityId, visits, EntityType.VISIT.getId()), this::onError);

        }

        if(mResult.equals(Result.success()))
            onTaskCompleted();
        return this.mResult;
    }

    public void onTaskCompleted(){

        repository.getDefaultSharePrefrences().edit().putString(Key.LAST_DATA_SYNC_DATETIME,LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).apply();
        mLocalBroadcastManager.sendBroadcast(new Intent(IntentAction.REMOTE_SYNC_COMPLETE));
    }

    public Consumer<Response[]> onComplete(Long[] entityIds, List<VisitEntity> visits, int entityTypeId){

        return param -> {

            for(Long entityId:entityIds) {
                db.entityMetadataDao().updateSyncStatus(entityId, Status.PUSHED.getCode());
                /* TODO
                /*TODO Find all entities that have Status NOT SYNCED(entites of same ID) & Delete them..*/
            }
        };
    }

    public List<Visit> createVisits (Long ...visitEntityId){

        List<VisitEntity> visitEntities = db.visitDao().getById(visitEntityId);

        if(visitEntities.size() > 0) {

            int visitIndex = 0;
            List<Visit> visits = new ArrayList<>();

            List<Long> visitIds = Observable.fromIterable(visitEntities).map(visitEntity -> visitEntity.getVisitId())
                    .toList().blockingGet();
            Observable.fromIterable(visitEntities).subscribe(
                    visitEntity -> {

                    }

            );

            //List<EncounterEntity> encounterEntities = db.encounterDao().getByEncounterByVisitId(EncounterEntity);

            int encounterIndex = 0;
            List<Encounter> encounters1 = new ArrayList<>();

            List<Long> encounterIds = Observable.fromIterable(visitEntities).map(visitEntity -> visitEntity.getVisitId())
                    .toList().blockingGet();
            Observable.fromIterable(visitEntities).subscribe(
                    visitEntity -> {
                        out.println("_______________________________________________");
                        out.println("visitEntity.getPatientId2---> " + visitEntity.getPatientId());
                        out.println("visitEntity.getVisitTypeId2---> " + visitEntity.getVisitTypeId());
                        out.println("visitEntity.getDateCreated2---> " + visitEntity.getDateCreated());
                        out.println("_______________________________________________");
                    }

            );

            List<EncounterEntity> encounterEntities = db.encounterDao().getByVisitId(visitIds);

            /*List<VisitEntity> visitEntities = db.visitDao().getByPatientId()
            Visit visitdao = new Visit();
            System.out.println(visitdao.getEncounters());
            System.out.println(visitdao.getLocation());
            System.out.println(visitdao.getPatient());
            System.out.println(visitdao.getVisitType());
            System.out.println(visitdao);
            Log.i(TAG, "VISIT INFO"+ visitdao.getEncounters());
            Log.i(TAG, "VISIT INFO"+ visitdao.getLocation());
            Log.i(TAG, "VISIT INFO"+ visitdao.getPatient());
            Log.i(TAG, "VISIT INFO"+ visitdao.getVisitType());
            Log.i(TAG, "VISIT INFO"+ visitdao);
            //Log.i(TAG, "Encounter Entities"+ new VisitEntity(visitIds, visitEntities));
            */




            //List<Long> encounterIds = Observable.fromIterable(encounterEntities)
            List<Long> encounterIds1 = Observable.fromIterable(encounterEntities).map(encounterEntity -> encounterEntity.getEncounterId())
                    .toList().blockingGet();
            Observable.fromIterable(encounterEntities).subscribe(
                    encounterEntity -> {

                    }
            );

                    /*.map(encounterEntity -> encounterEntity.getEncounterId())
                    .toList()
                    .blockingGet();*/

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
