package zm.gov.moh.core.service.worker;

import android.content.Context;
import android.content.Intent;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

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
import zm.gov.moh.core.model.Patient;
import zm.gov.moh.core.model.PatientIdentifier;
import zm.gov.moh.core.model.PersonAttribute;
import zm.gov.moh.core.model.Response;
import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.domain.ObsEntity;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.ServiceManager;
import zm.gov.moh.core.utils.ConcurrencyUtils;

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
                    .timeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    .subscribe(onComplete(unpushedPatientEntityId, EntityType.PATIENT.getId()), this::onError);
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
