package zm.gov.moh.core.service.worker;

import android.content.Context;
import android.content.Intent;

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
import zm.gov.moh.core.Constant;
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

public class PushVisitDataRemoteWorker extends RemoteWorker {

    public PushVisitDataRemoteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams){
        super(context, workerParams);
    }

    //Get Pushed Entity IDs
    public long[] getPushedEntityMetadata(){
        long[] pushedEntityIDs = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.VISIT.getId(), Status.PUSHED.getCode());
        return pushedEntityIDs;
    }

    //Get Pushed Entity IDs
    public Long[] getUnpushedVisitEntityId(long[] UnpushedVisitEntityIDs){
        long offset = Constant.LOCAL_ENTITY_ID_OFFSET;
        Long[] unpushedVisitEntityID = db.visitDao().findEntityNotWithId(offset, UnpushedVisitEntityIDs);
        return unpushedVisitEntityID;
    }



    @Override
    @NonNull
    public Result doWork() {


        long batchVersion = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        Long[] unpushedVisitEntityID = getUnpushedVisitEntityId(getPushedEntityMetadata());



        List<Visit> patientVisits = createVisits(unpushedVisitEntityID);

        if (patientVisits.size() > 0)
            restApi.putVisit(accessToken, batchVersion, patientVisits.toArray(new Visit[patientVisits.size()]))
                    .timeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    .subscribe(onComplete(unpushedVisitEntityID, EntityType.VISIT.getId()), this::onError);

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

                EntityMetadata entityMetadata = new EntityMetadata(entityId,entityTypeId, Status.PUSHED.getCode());
                db.entityMetadataDao().insert(entityMetadata);
            }
        };
    }

    public List<VisitEntity> getVisitEntities(Long[] entityIds)
    {
        List<VisitEntity> visitEntities = db.visitDao().getById(entityIds);
        return  visitEntities;
    }

    public List<Long> getVisitIds(List<VisitEntity> visitEntities)
    {
        List<Long> visitIds = Observable.fromIterable(visitEntities).map(visitEntity -> visitEntity.getVisitId())
                .toList().blockingGet();
        return visitIds;
    }

    public List<EncounterEntity> getEncounterEntities(List<Long> visitIds)
    {

        List<EncounterEntity> encounterEntities = db.encounterDao().getByVisitId(visitIds);
        return encounterEntities;
    }

    public List<Long> getEncounterIds(List<EncounterEntity> encounterEntities)
    {
        List<Long> encounterIds = Observable.fromIterable(encounterEntities)
                .map(encounterEntity -> encounterEntity.getEncounterId())
                .toList()
                .blockingGet();
        return encounterIds;
    }

    public List<ObsEntity> getObsEntities(List<Long> encounterIds)
    {
        List<ObsEntity> obsEntities = db.obsDao().getObsByEncounterId(encounterIds);
        return obsEntities;
    }

    public List<EncounterEntity> getVisitEncounter(List<EncounterEntity> encounterEntities,VisitEntity visitEntity)
    {
        List<EncounterEntity> visitEncounter = Observable.fromIterable(encounterEntities)
                .filter(encounterEntity -> (encounterEntity.getVisitId().longValue() == visitEntity.getVisitId().longValue())).toList().blockingGet();
        return visitEncounter;
    }

    public List<ObsEntity> getEncounterObs( List<ObsEntity> obsEntities,EncounterEntity encounterEntity)
    {
        List<ObsEntity> encounterObs = Observable.fromIterable(obsEntities)
                .filter(obsEntity -> (obsEntity.getEncounterId() == encounterEntity.getEncounterId()))
                .toList()
                .blockingGet();
        return encounterObs;
    }

    public Obs[] getObs(List<ObsEntity> encounterObs)
    {
        Obs[] obs = Observable.fromIterable(encounterObs)
                .map((this::normalizeObs))
                .toList()
                .blockingGet()
                .toArray(new Obs[encounterObs.size()]);
        return obs;

    }

    public Encounter[] getEncounter(List<EncounterEntity> visitEncounter, List<ObsEntity> obsEntities )
    {
        Encounter[] encounters = new Encounter[visitEncounter.size()];
        int index = 0;
        for (EncounterEntity encounterEntity : visitEncounter) {
            if (encounterEntity == null)
                continue;
            Encounter encounter = normalizeEncounter(encounterEntity);
            Obs[] obs=getObs(getEncounterObs(obsEntities,encounterEntity));
            encounter.setObs(obs);
            encounters[index++] = encounter;
        }
        return encounters;
    }


    public List<Visit> createVisits (Long ... visitEntityIds){
        List<VisitEntity> visitEntities = getVisitEntities(visitEntityIds);
        if(visitEntities.size() > 0) {
            List<Visit> visits = new ArrayList<>();
            List<Long> visitIds = getVisitIds(visitEntities);
            List<EncounterEntity> encounterEntities = getEncounterEntities(visitIds);
            List<Long> encounterIds = getEncounterIds(encounterEntities);
            List<ObsEntity> obsEntities = getObsEntities(encounterIds);
            for (VisitEntity visitEntity : visitEntities) {
                try {
                    Visit.Builder visit = normalizeVisit(visitEntity);
                    List<EncounterEntity> visitEncounter = getVisitEncounter(encounterEntities,visitEntity);
                    visits.add(visit.setEncounters(getEncounter(visitEncounter,obsEntities)).build());

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
