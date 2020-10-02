/*package zm.gov.moh.core.mocks;

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
import zm.gov.moh.core.service.worker.RemoteWorker;

public class PushVisitDataRemoteWorkerMock extends RemoteWorker {

    public PushVisitDataRemoteWorkerMock(Context visitDaoMock) {
        super(visitDaoMock, (WorkerParameters) getUnpushedVisitEntityId());
    }

    public  Object getUnpushedVisitEntityId() {
        long[] unpushedEntityIDs = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.VISIT.getId(), Status.NOT_PUSHED.getCode());
        db.entityMetadataDao().insert((EntityMetadata) getUnpushedVisitEntityId());
        return unpushedEntityIDs;
    }


    //Get Pushed Entity IDs
    private long[] getPushedEntityMetadata(){
        return db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.VISIT.getId(), Status.PUSHED.getCode());
    }

    //Get Pushed Entity IDs
    private static Long[] getUnpushedVisitEntityId(long[] UnpushedVisitEntityIDs){
        long offset = Constant.LOCAL_ENTITY_ID_OFFSET;
        return db.visitDao().findEntityNotWithId(offset, UnpushedVisitEntityIDs);
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

    private Consumer<Response[]> onComplete(Long[] entityIds, int entityTypeId){

        return param -> {

            for(Long entityId:entityIds) {

                EntityMetadata entityMetadata = new EntityMetadata(entityId,entityTypeId, Status.PUSHED.getCode());
                db.entityMetadataDao().insert(entityMetadata);
            }
        };
    }

    private List<VisitEntity> getVisitEntities(Long[] entityIds)
    {
        return db.visitDao().getById(entityIds);
    }

    private List<Long> getVisitIds(List<VisitEntity> visitEntities)
    {
        return Observable.fromIterable(visitEntities).map(VisitEntity::getVisitId)
                .toList().blockingGet();
    }

    private List<EncounterEntity> getEncounterEntities(List<Long> visitIds)
    {
        return db.encounterDao().getByVisitId(visitIds);
    }

    private List<Long> getEncounterIds(List<EncounterEntity> encounterEntities)
    {
        return Observable.fromIterable(encounterEntities)
                .map(encounterEntity -> encounterEntity.getEncounterId())
                .toList()
                .blockingGet();
    }

    private List<ObsEntity> getObsEntities(List<Long> encounterIds)
    {
        return db.obsDao().getObsByEncounterId(encounterIds);
    }

    private List<EncounterEntity> getVisitEncounter(List<EncounterEntity> encounterEntities, VisitEntity visitEntity)
    {
        return Observable.fromIterable(encounterEntities)
                .filter(encounterEntity -> (encounterEntity.getVisitId().longValue() == visitEntity.getVisitId().longValue())).toList().blockingGet();
    }

    private List<ObsEntity> getEncounterObs(List<ObsEntity> obsEntities, EncounterEntity encounterEntity)
    {
        return Observable.fromIterable(obsEntities)
                .filter(obsEntity -> (obsEntity.getEncounterId() == encounterEntity.getEncounterId()))
                .toList()
                .blockingGet();
    }

    private Obs[] getObs(List<ObsEntity> encounterObs)
    {
        return Observable.fromIterable(encounterObs)
                .map((this::normalizeObs))
                .toList()
                .blockingGet()
                .toArray(new Obs[encounterObs.size()]);

    }

    private Encounter[] getEncounter(List<EncounterEntity> visitEncounter, List<ObsEntity> obsEntities)
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

                catch (Exception ignored){
                }
            }
            return visits;
        }
        return null;
    }



    private Obs normalizeObs(ObsEntity obsEntity){

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

    private Visit.Builder normalizeVisit(VisitEntity visitEntity) throws Exception {

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

    private Encounter normalizeEncounter(EncounterEntity encounterEntity){

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

}*/
