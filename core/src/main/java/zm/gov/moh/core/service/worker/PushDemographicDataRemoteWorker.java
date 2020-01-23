package zm.gov.moh.core.service.worker;

import android.content.Context;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import io.reactivex.functions.Consumer;
import zm.gov.moh.core.Constant;
import zm.gov.moh.core.model.Patient;
import zm.gov.moh.core.model.PatientIdentifier;
import zm.gov.moh.core.model.PersonAttribute;
import zm.gov.moh.core.model.Response;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;

public class PushDemographicDataRemoteWorker extends RemoteWorker {

    public PushDemographicDataRemoteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams){
        super(context, workerParams);
    }

    @Override
    @NonNull
    public Result doWork() {

        long batchVersion = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        long[] pushedEntityId = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.PATIENT.getId(), Status.PUSHED.getCode());
        final long offset = Constant.LOCAL_ENTITY_ID_OFFSET;

        List<Patient> patients;
        int index = 0;
        Long[] unpushedPatientEntityId = db.patientDao().findEntityNotWithId(offset,pushedEntityId);
        if(unpushedPatientEntityId.length != 0) {

            patients = new ArrayList<>();

            for(Long patientEntityId : unpushedPatientEntityId){

                try {

                    Patient patient = createPatient(patientEntityId);
                    if (patient != null)
                        patients.add(patient);
                }catch (Exception e){

                }
            }


            if(patients.size() > 0) {

                restApi.putPatients(accessToken, batchVersion, patients.toArray(new Patient[patients.size()]))
                        .timeout(TIMEOUT, TimeUnit.MILLISECONDS)
                        .subscribe(onComplete(unpushedPatientEntityId, EntityType.PATIENT.getId()), this::onError);
            }
        }else
            return mResult;

        return this.mResult;
    }

    public Consumer<Response[]> onComplete(Long[] entityIds, int entityTypeId){

        return param -> {

            for(Long entityId:entityIds) {

                EntityMetadata entityMetadata = new EntityMetadata(entityId,entityTypeId, Status.PUSHED.getCode());
                db.entityMetadataDao().insert(entityMetadata);
            }
        };
    }

    public Patient createPatient(long patientId) throws Exception{

       Person person = db.personDao().findById(patientId);
       PersonName personName = db.personNameDao().findByPersonId(patientId);
       PersonAddress personAddress = db.personAddressDao().findByPersonId(patientId);
       List<PatientIdentifier> patientIdentifiers = db.patientIdentifierDao().findAllByPatientId(patientId);
       List<PersonAttribute> personAttributes = db.personAttributeDao().findByPersonId(patientId);

       if((person != null || personName != null || personAddress != null) && patientIdentifiers.size() > 1) {

           return new Patient.Builder()
                   .setPerson(person)
                   .setPersonName(personName)
                   .setPersonAddress(personAddress)
                   .setAttributes(personAttributes)
                   .setIdentifiers(patientIdentifiers)
                   .build();
       }
       else
           throw new Exception("Inadequate arguments");
    }
}
