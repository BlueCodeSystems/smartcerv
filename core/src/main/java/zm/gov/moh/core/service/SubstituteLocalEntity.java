package zm.gov.moh.core.service;

import android.content.Intent;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import zm.gov.moh.core.Constant;
import zm.gov.moh.core.model.EntityId;
import zm.gov.moh.core.model.IntentAction;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityType;

public class SubstituteLocalEntity extends RemoteService {

    public SubstituteLocalEntity(){
        super(ServiceManager.Service.SUBSTITUTE_LOCAL_ENTITY);
    }

    @Override
    protected void executeAsync() {

        EntityType entityType = (EntityType) mBundle.getSerializable(Key.ENTITY_TYPE);

        if(entityType == null){
            notifyInterrupted();

            return;
        }

        switch (entityType){

            case PATIENT:
                long[] pushedPatientEntities = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.PATIENT.getId(), Status.PUSHED.getCode());
                EntityId[] entityIds = db.patientIdentifierDao().getSyncedEntityId(Constant.LOCAL_ENTITY_ID_OFFSET,3);
                substituteLocalPatientEntity(pushedPatientEntities);
                updateLocalVisitEntity(entityIds);

                mBundle.putSerializable(Key.ENTITY_TYPE, EntityType.VISIT);
                ServiceManager.getInstance(getApplicationContext())
                        .setService(ServiceManager.Service.PUSH_ENTITY_REMOTE)
                        .putExtras(mBundle)
                        .start();

                notifyCompleted();
                break;

            case VISIT:
                repository.getDefaultSharePrefrences().edit().putString(Key.LAST_SYNC_DATE,LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).apply();
                mLocalBroadcastManager.sendBroadcast(new Intent(IntentAction.REMOTE_SYNC_COMPLETE));
                notifyCompleted();
                break;
        }
    }

    public void updateLocalVisitEntity(EntityId[] entityIds){

        for(EntityId entityId: entityIds){

            //Patient
            db.personDao().replacePerson(entityId.getLocal(), entityId.getRemote());
            db.patientDao().replacePatient(entityId.getLocal(), entityId.getRemote());
            db.personNameDao().replacePerson(entityId.getLocal(),entityId.getRemote());
            db.personAddressDao().replacePerson(entityId.getLocal(),entityId.getRemote());
            db.patientIdentifierDao().replacePatient(entityId.getLocal(),entityId.getRemote());

            //Visit
            db.visitDao().replaceLocalPatientId(entityId.getLocal(),entityId.getRemote());
            db.encounterDao().replaceLocalPatientId(entityId.getLocal(),entityId.getRemote());
            db.obsDao().replaceLocalPersonId(entityId.getLocal(),entityId.getRemote());
        }
    }

    public void substituteLocalPatientEntity(long[] entityId){




    }
}
