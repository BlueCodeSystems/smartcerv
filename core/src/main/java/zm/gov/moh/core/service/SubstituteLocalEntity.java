package zm.gov.moh.core.service;

import zm.gov.moh.core.Constant;
import zm.gov.moh.core.model.EntityId;
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
                long[] pushedVisitEntities = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.VISIT.getId(), Status.PUSHED.getCode());
                substituteLocalVisitEntity(pushedVisitEntities);
                notifyCompleted();
                break;
        }



        notifyCompleted();
    }

    public void updateLocalVisitEntity(EntityId[] entityIds){

        for(EntityId entityId: entityIds){

            db.visitDao().replaceLocalPatientId(entityId.getLocal(),entityId.getRemote());
            db.encounterDao().replaceLocalPatientId(entityId.getLocal(),entityId.getRemote());
            db.obsDao().replaceLocalPersonId(entityId.getLocal(),entityId.getRemote());
        }
    }

    public void substituteLocalPatientEntity(long[] entityId){

        db.personDao().deleteById(entityId);
        db.patientDao().deleteById(entityId);
        db.personNameDao().deleteByPersonId(entityId);
        db.personAddressDao().deleteByPersonId(entityId);
        db.patientIdentifierDao().deleteByPatientId(entityId);
    }

    public void substituteLocalVisitEntity(long[] visitEntityIds){

        long[] encounterEntityIds = db.encounterDao().getByVisitId(visitEntityIds);
        long[] obsEntitiesIds = db.obsDao().getObsByEncounterId(encounterEntityIds);

        db.visitDao().deleteById(visitEntityIds);
        db.encounterDao().deleteById(encounterEntityIds);
        db.obsDao().deleteById(obsEntitiesIds);
    }


}
