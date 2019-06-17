package zm.gov.moh.core.service;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import java.util.List;

import io.reactivex.functions.Consumer;
import zm.gov.moh.core.Constant;
import zm.gov.moh.core.model.PatientIdentifier;
import zm.gov.moh.core.model.PersonAttribute;
import zm.gov.moh.core.model.Response;
import zm.gov.moh.core.model.Patient;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class PushDataRemote extends RemoteService {

    public PushDataRemote(){
        super(ServiceManager.Service.PUSH_ENTITY_REMOTE);
    }

    @Override
    protected void executeAsync() {

        long batchVersion = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        for (EntityType entitiyId:EntityType.values())
            ConcurrencyUtils.consumeAsync(this::pushInfoRemote, this::onError, entitiyId, batchVersion);

    }

   protected void pushInfoRemote(EntityType entityType, long batchVersion){

        long[] pushedEntityId = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(entityType.getId(), Status.PUSHED.getCode());
        final long offset = Constant.LOCAL_ENTITY_ID_OFFSET;

        switch (entityType){

            case PATIENT:
                onTaskStarted();
                Patient[] patients;
                int index = 0;

                Long[] unpushedPatientEntityId = db.patientDao().findEntityNotWithId(offset,pushedEntityId);
                if(unpushedPatientEntityId.length != 0) {

                    patients = new Patient[unpushedPatientEntityId.length];

                    for(Long patientEntityId : unpushedPatientEntityId){

                        Patient patient = createPatient(patientEntityId);
                        if(patient != null)
                            patients[index++] = patient;
                    }

                    restApi.putPatients(accessToken, batchVersion, patients)
                            .subscribe(onComplete(unpushedPatientEntityId, entityType.getId()), this::onError);


                }else
                    onTaskCompleted();
                break;

            case VISIT:
        }
    }

    public Consumer<Response[]> onComplete(Long[] entityIds, int entityTypeId){


        return param -> {

            for(Long entityId:entityIds) {

                EntityMetadata entityMetadata = new EntityMetadata(entityId,entityTypeId, Status.PUSHED.getCode());
                db.entityMetadataDao().insert(entityMetadata);
            }
            onTaskCompleted();
        };
    }

    public Patient createPatient(long patientId){

       Person person = db.personDao().findById(patientId);
       PersonName personName = db.personNameDao().findPersonNameById(patientId);
       PersonAddress personAddress = db.personAddressDao().findByPersonId(patientId);
       List<PatientIdentifier> patientIdentifiers = db.patientIdentifierDao().findAllByPatientId(patientId);
       List<PersonAttribute> personAttributes = db.personAttributeDao().findByPersonId(patientId);

       return new Patient.Builder()
               .setPerson(person)
               .setPersonName(personName)
               .setPersonAddress(personAddress)
               .setAttributes(personAttributes)
               .setIdentifiers(patientIdentifiers)
               .build();
    }
}