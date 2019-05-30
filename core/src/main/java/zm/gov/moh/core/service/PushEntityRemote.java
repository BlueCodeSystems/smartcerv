package zm.gov.moh.core.service;

import android.content.Intent;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.List;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import io.reactivex.functions.Consumer;
import zm.gov.moh.core.Constant;
import zm.gov.moh.core.model.Response;
import zm.gov.moh.core.repository.database.entity.SynchronizableEntity;
import zm.gov.moh.core.repository.database.entity.domain.Encounter;
import zm.gov.moh.core.repository.database.entity.domain.Obs;
import zm.gov.moh.core.repository.database.entity.domain.Patient;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.Visit;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;

public class PushEntityRemote extends RemoteService {

    public PushEntityRemote(){
        super(ServiceManager.Service.PUSH_ENTITY_REMOTE);
    }

    @Override
    protected void executeAsync() {

        long batchVersion = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        for (EntityType entitiyId:EntityType.values())
            repository.consumeAsync(this::pushEntityRemote, this::onError, entitiyId, batchVersion);
    }

    protected void pushEntityRemote(EntityType entityType, long batchVersion){

        long[] pushedEntityId = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(entityType.getId(), Status.PUSHED.getCode());
        final int RETRY_ATTEMPTS = 5;
        final long offset = Constant.LOCAL_ENTITY_ID_OFFSET;

        onTaskStarted();

        switch (entityType){

            case PERSON_NAME:
                PersonName[] unpushedPersonName = db.personNameDao().findEntityNotWithId(offset, pushedEntityId);
                if(unpushedPersonName.length != 0) {
                    restApi.putPersonNames(accesstoken, batchVersion, unpushedPersonName)
                            .retry(RETRY_ATTEMPTS)
                            .subscribe(onComplete(unpushedPersonName, entityType.getId()), this::onError);
                }else
                    onTaskCompleted();
                break;

            case PERSON:
                Person[] unpushedPerson = db.personDao().findEntityNotWithId(offset,pushedEntityId);
                if(unpushedPerson.length != 0) {
                    restApi.putPersons(accesstoken, batchVersion, unpushedPerson)
                            .retry(RETRY_ATTEMPTS)
                            .subscribe(onComplete(unpushedPerson, entityType.getId()), this::onError);
                }else
                    onTaskCompleted();
                break;

            case PERSON_ADDRESS:
                PersonAddress[] unpushedPersonAddress = db.personAddressDao().findEntityNotWithId(offset,pushedEntityId);
                if(unpushedPersonAddress.length != 0) {
                    restApi.putPersonAddresses(accesstoken, batchVersion, unpushedPersonAddress)
                            .retry(RETRY_ATTEMPTS)
                            .subscribe(onComplete(unpushedPersonAddress, entityType.getId()), this::onError);
                }else
                    onTaskCompleted();
                break;

            case PATIENT:
                Patient[] unpushedPatient = db.patientDao().findEntityNotWithId(offset,pushedEntityId);
                if(unpushedPatient.length != 0) {
                    restApi.putPatients(accesstoken, batchVersion, unpushedPatient)
                            .retry(RETRY_ATTEMPTS)
                            .subscribe(onComplete(unpushedPatient, entityType.getId()), this::onError);
                }else
                    onTaskCompleted();
                break;

            case PATIENT_IDENTIFIER:
                PatientIdentifier[] unpushedPatietIdentifier = db.patientIdentifierDao().findEntityNotWithId(offset,pushedEntityId);
                if(unpushedPatietIdentifier.length != 0) {
                    restApi.putPatientIdentifiers(accesstoken, batchVersion, unpushedPatietIdentifier)
                            .retry(RETRY_ATTEMPTS)
                            .subscribe(onComplete(unpushedPatietIdentifier, entityType.getId()), this::onError);
                }else
                    onTaskCompleted();
                break;

            case OBS:
                Obs[] unpushedObs = db.obsDao().findEntityNotWithId(offset,pushedEntityId);
                if(unpushedObs.length != 0) {
                    restApi.putObs(accesstoken, batchVersion, unpushedObs)
                            .retry(RETRY_ATTEMPTS)
                            .subscribe(onComplete(unpushedObs, entityType.getId()), this::onError);
                }else
                    onTaskCompleted();
                break;

            case ENCOUNTER:
                Encounter[] unpushedEncounter = db.encounterDao().findEntityNotWithId(offset,pushedEntityId);
                if(unpushedEncounter.length != 0) {
                    restApi.putEncounter(accesstoken, batchVersion, unpushedEncounter)
                            .retry(RETRY_ATTEMPTS)
                            .subscribe(onComplete(unpushedEncounter, entityType.getId()), this::onError);
                }else
                    onTaskCompleted();
                break;

            case VISIT:
                Visit[] unpushedVisit = db.visitDao().findEntityNotWithId(offset,pushedEntityId);
                if(unpushedVisit.length != 0) {
                    restApi.putVisit(accesstoken, batchVersion, unpushedVisit)
                            .retry(RETRY_ATTEMPTS)
                            .subscribe(onComplete(unpushedVisit, entityType.getId()), this::onError);
                }else
                    onTaskCompleted();
                break;
        }
    }

    public Consumer<Response[]> onComplete(SynchronizableEntity[] entities, int entityTypeId){


        return param -> {

            for(SynchronizableEntity entity:entities) {

                EntityMetadata entityMetadata = new EntityMetadata(entity.getId(),entityTypeId, Status.PUSHED.getCode());
                db.entityMetadataDao().insert(entityMetadata);
            }
            onTaskCompleted();
        };
    }

    protected void notifySyncCompleted() {
        Intent intent = new Intent(ServiceManager.IntentAction.PUSH_ENTITY_REMOTE_COMPLETE);
        intent.putExtras(mBundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);


    }

    @Override
    public void onError(Throwable throwable) {
        super.onError(throwable);

        Intent intent = new Intent(ServiceManager.IntentAction.PUSH_ENTITY_REMOTE_INTERRUPT);
        intent.putExtras(mBundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}