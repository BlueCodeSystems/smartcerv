package zm.gov.moh.core.service;

import zm.gov.moh.core.Constant;

public class SubstituteLocalEntity extends PersistService {

    public SubstituteLocalEntity(){
        super(ServiceManager.Service.SUBSTITUTE_LOCAL_ENTITY);
    }

    @Override
    protected void executeAsync() {



       long[] ids = db.patientIdentifierDao().getSynced(Constant.LOCAL_ENTITY_ID_OFFSET);
      int l = ids.length;

      substituteLocalEntity(ids);

    }

    public void substituteLocalEntity(long[] entityId){

        db.personDao().deleteById(entityId);
        db.patientDao().deleteById(entityId);
        db.personNameDao().deleteByPersonId(entityId);
        db.personAddressDao().deleteByPersonId(entityId);
        db.patientIdentifierDao().deleteByPatientId(entityId);

        notifyCompleted();
    }
}
