package zm.gov.moh.core.service;

import android.content.Intent;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

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
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class PushEntityRemote extends RemoteService {

    public PushEntityRemote(){
        super(ServiceManager.Service.PUSH_ENTITY_REMOTE);
    }

    @Override
    protected void executeAsync() {

        long batchVersion = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        for (EntityType entitiyId:EntityType.values())
            ConcurrencyUtils.consumeAsync(this::pushEntityRemote, this::onError, entitiyId, batchVersion);

        notifyCompleted();
    }

    protected void pushEntityRemote(EntityType entityType, long batchVersion){

        long[] pushedEntityId = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(entityType.getId(), Status.PUSHED.getCode());
        final long offset = Constant.LOCAL_ENTITY_ID_OFFSET;

        onTaskStarted();

        switch (entityType){

            case PERSON_NAME:
                PersonName[] unpushedPersonName = db.personNameDao().findEntityNotWithId(offset, pushedEntityId);
                if(unpushedPersonName.length != 0) {
                    restApi.putPersonNames(accessToken, batchVersion, unpushedPersonName)
                            .subscribe(onComplete(unpushedPersonName, entityType.getId()), this::onError);
                }else
                    onTaskCompleted();
                break;

            case PERSON:
                Person[] unpushedPerson = db.personDao().findEntityNotWithId(offset,pushedEntityId);
                if(unpushedPerson.length != 0) {
                    restApi.putPersons(accessToken, batchVersion, unpushedPerson)
                            .subscribe(onComplete(unpushedPerson, entityType.getId()), this::onError);
                }else
                    onTaskCompleted();
                break;

            case PERSON_ADDRESS:
                PersonAddress[] unpushedPersonAddress = db.personAddressDao().findEntityNotWithId(offset,pushedEntityId);
                if(unpushedPersonAddress.length != 0) {
                    restApi.putPersonAddresses(accessToken, batchVersion, unpushedPersonAddress)
                            .subscribe(onComplete(unpushedPersonAddress, entityType.getId()), this::onError);
                }else
                    onTaskCompleted();
                break;

            case PATIENT:
                Patient[] unpushedPatient = db.patientDao().findEntityNotWithId(offset,pushedEntityId);
                if(unpushedPatient.length != 0) {
                    restApi.putPatients(accessToken, batchVersion, unpushedPatient)
                            .subscribe(onComplete(unpushedPatient, entityType.getId()), this::onError);
                }else
                    onTaskCompleted();
                break;

            case PATIENT_IDENTIFIER:
                PatientIdentifier[] unpushedPatietIdentifier = db.patientIdentifierDao().findEntityNotWithId(offset,pushedEntityId);
                if(unpushedPatietIdentifier.length != 0) {
                    restApi.putPatientIdentifiers(accessToken, batchVersion, unpushedPatietIdentifier)
                            .subscribe(onComplete(unpushedPatietIdentifier, entityType.getId()), this::onError);
                }else
                    onTaskCompleted();
                break;

            case OBS:
                Obs[] unpushedObs = db.obsDao().findEntityNotWithId(offset,pushedEntityId);
                if(unpushedObs.length != 0) {
                    restApi.putObs(accessToken, batchVersion, unpushedObs)
                            .subscribe(onComplete(unpushedObs, entityType.getId()), this::onError);
                }else
                    onTaskCompleted();
                break;

            case ENCOUNTER:
                Encounter[] unpushedEncounter = db.encounterDao().findEntityNotWithId(offset,pushedEntityId);
                if(unpushedEncounter.length != 0) {
                    restApi.putEncounter(accessToken, batchVersion, unpushedEncounter)
                            .subscribe(onComplete(unpushedEncounter, entityType.getId()), this::onError);
                }else
                    onTaskCompleted();
                break;

            case VISIT:
                Visit[] unpushedVisit = db.visitDao().findEntityNotWithId(offset,pushedEntityId);
                if(unpushedVisit.length != 0) {
                    restApi.putVisit(accessToken, batchVersion, unpushedVisit)
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
}