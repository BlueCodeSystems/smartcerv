package zm.gov.moh.core.service.worker;

import android.content.Context;

import com.google.common.collect.ObjectArrays;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.core.content.res.TypedArrayUtils;
import androidx.work.WorkerParameters;
import io.reactivex.functions.Consumer;
import zm.gov.moh.core.Constant;
import zm.gov.moh.core.model.Patient;
import zm.gov.moh.core.model.PatientIdentifier;
import zm.gov.moh.core.model.PersonAttribute;
import zm.gov.moh.core.model.Response;
import zm.gov.moh.core.repository.database.Migrations;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonAttributeEntity;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;

public class PushDemographicDataRemoteWorker extends RemoteWorker {
    LocalDateTime dataSyncDate;
    public PushDemographicDataRemoteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams){
        super(context, workerParams);
    }

    @Override
    @NonNull
    public Result doWork() {

        try {
            long batchVersion = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
            long[] pushedEntityId = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.PATIENT.getId(), Status.PUSHED.getCode());

            int pushedSize = pushedEntityId.length;
            Long[] editEntityId = db.entityMetadataDao().findByStatus(Status.NOT_PUSHED.getCode());
            int editSize = editEntityId.length;

            final long offset = Constant.LOCAL_ENTITY_ID_OFFSET;
            dataSyncDate = LocalDateTime.parse(lastDataSyncDate);

            //Create new remote patients
            List<Patient> patients = new ArrayList<>();
            //Long[] unpushedPatientEntityId = db.patientDao().findEntityNotWithId(offset, pushedEntityId);
            Long[] unpushedPatientEntityId = db.patientDao().findEntityNotWithId2(offset, EntityType.PATIENT.getId(), Status.PUSHED.getCode());

            unpushedPatientEntityId = ObjectArrays.concat(unpushedPatientEntityId, editEntityId, Long.class);
            if (unpushedPatientEntityId.length != 0) {

                for (Long patientEntityId : unpushedPatientEntityId) {

                    try {

                        Patient patient = createPatient(patientEntityId, dataSyncDate);
                        if (patient != null)
                            patients.add(patient);
                    } catch (Exception e) {

                    }
                }
            }

            //Update patient details remote
          /*  pushedEntityId = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.PATIENT.getId(), Status.PUSHED.getCode(), dataSyncDate);
            if (pushedEntityId.length > 0) {

                for (Long patientEntityId : pushedEntityId) {

                    try {

                        Patient patient = createPatient(patientEntityId, dataSyncDate);
                        if (patient != null)
                            patients.add(patient);
                    } catch (Exception e) {

                    }
                }
            }*/

            if (patients.size() > 0) {

                restApi.putPatients(accessToken, batchVersion, patients.toArray(new Patient[patients.size()]))
                        .timeout(TIMEOUT, TimeUnit.MILLISECONDS)
                        .subscribe(onComplete(unpushedPatientEntityId, EntityType.PATIENT.getId()), this::onError);
            }

            return this.mResult;
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }

    public Consumer<Response[]> onComplete(Long[] entityIds, int entityTypeId){

        return param -> {

            for(Long entityId:entityIds) {

                EntityMetadata entityMetadata = db.entityMetadataDao().findEntityById(entityId);
                if(entityMetadata == null)
                    entityMetadata = new EntityMetadata(entityId,entityTypeId);

                entityMetadata.setRemoteStatusCode( Status.PUSHED.getCode());
                entityMetadata.setLastModified(LocalDateTime.now());

                db.entityMetadataDao().insert(entityMetadata);
            }
        };
    }

    public Patient createPatient(final long patientId, final LocalDateTime lastModified) throws Exception{

        Person person = db.personDao().findById(patientId);

        //PersonAttributeEntity personAttributes = db.personAttributeDao().findByPersonEntityId(patientId);
        PersonName personName = db.personNameDao().findByPersonId(patientId, lastModified);
        PersonAddress personAddress = db.personAddressDao().findByPersonId(patientId, lastModified);
        List<PatientIdentifier> patientIdentifiers = db.patientIdentifierDao().findAllByPatientId(patientId, lastModified);
        List<PersonAttribute> personAttributes = db.personAttributeDao().findByPersonId(patientId, lastModified);

        if((person != null || personName != null || personAddress != null) && ((patientIdentifiers.size() > 1 && person.getVoided() == 0) || person.getUuid() != null)) {


            return new Patient.Builder()
                    .setPerson(person)
                    .setPersonName(personName)
                    .setPersonAddress(personAddress)
                    .setAttributes(personAttributes)
                    .setIdentifiers((patientIdentifiers.size() == 0)? null:patientIdentifiers)
                    .build();
        }
        else
            throw new Exception("Inadequate arguments");
    }
}
