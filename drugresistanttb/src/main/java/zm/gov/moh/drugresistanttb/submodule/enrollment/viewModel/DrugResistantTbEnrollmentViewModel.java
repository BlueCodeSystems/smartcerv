package zm.gov.moh.drugresistanttb.submodule.enrollment.viewModel;

import android.app.Application;
import android.os.Bundle;

import org.threeten.bp.LocalDateTime;


import androidx.lifecycle.MutableLiveData;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.drugresistanttb.OpenmrsConfig;
import zm.gov.moh.drugresistanttb.submodule.enrollment.view.DrugResistantTbEnrollmentActivity;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.worker.RemoteWorker;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class DrugResistantTbEnrollmentViewModel extends BaseAndroidViewModel {

    private MutableLiveData<String>actionEmitter;

    //TODO:Must read value from global context
    private final long MDR_ID_TYPE = 7;
    //private final long OPENMRS_ID_TYPE = 3;


    public DrugResistantTbEnrollmentViewModel(Application application){
        super(application);

    }

    public MutableLiveData<String> getActionEmitter() {
        if(actionEmitter == null)
            actionEmitter = new MutableLiveData<>();

        return actionEmitter;
    }

    public void enroll(Bundle bundle){

        ConcurrencyUtils.consumeAsync(this::enrollClient, this::onError, bundle);
    }

    public void edit(Bundle bundle){

        ConcurrencyUtils.consumeAsync(this::editClient, this::onError, bundle);
    }

    public void enrollClient(Bundle bundle){

        long  personId = bundle.getLong(Key.PERSON_ID);
        long locationId = (long) bundle.get(Key.LOCATION_ID);
        String identifier = (String) bundle.get(Key.PATIENT_ID);
        String action = bundle.getString(Key.ACTION);
        long patientIdentifierIdmdrpiz = DatabaseUtils.generateLocalId(getRepository().getDatabase().patientIdentifierDao()::getMaxId);

        PatientIdentifierEntity patientIdentifier =  db.patientIdentifierDao().getByLocationType(personId,locationId, OpenmrsConfig.IDENTIFIER_TYPE_MDRPIZ_UUID);

        if(patientIdentifier == null) {


            //Create database entity instances

            //patient id MDRIZ
            patientIdentifier = new PatientIdentifierEntity(patientIdentifierIdmdrpiz, personId,
                    identifier,
                    MDR_ID_TYPE, preffered(),
                    locationId, LocalDateTime.now());
        }else {

            //Void previous identifier
            patientIdentifier.setVoided((short)1);
            getRepository().getDatabase().patientIdentifierDao().insert(patientIdentifier);

            //Mutate previous identifier instance and insert it with a new PK
            patientIdentifier.setVoided((short)0);
            patientIdentifier.setIdentifier(identifier);
            patientIdentifier.setDateCreated(LocalDateTime.now());
            patientIdentifier.setDateChanged(LocalDateTime.now());
            patientIdentifier.setPatientIdentifierId(patientIdentifierIdmdrpiz);
        }

        //persist database entity instances asynchronously into the database
        ConcurrencyUtils.consumeAsync(getRepository().getDatabase().patientIdentifierDao()::insert, this::onError, patientIdentifier);
        if(action != null && action.equals(DrugResistantTbEnrollmentActivity.Action.ENROLL_PATIENT))
            getActionEmitter().postValue(DrugResistantTbEnrollmentActivity.Action.ENROLL_PATIENT);

    }

    public void editClient(Bundle bundle){
        this.enrollClient(bundle);
        long  patientId = bundle.getLong(Key.PERSON_ID);
        Person person = getRepository().getDatabase().personDao().findById(patientId);

        if(person != null){
            db.personDao().updateNRCNumberBydID(patientId,bundle.getString(Key.NRC_NUMBER),LocalDateTime.now());
        }

        EntityMetadata entityMetadata = db.entityMetadataDao().findEntityById(patientId);
        if(entityMetadata == null)
            entityMetadata = new EntityMetadata(patientId, EntityType.PATIENT.getId());

        entityMetadata.setLastModified(LocalDateTime.now());
        entityMetadata.setRemoteStatusCode(RemoteWorker.Status.NOT_PUSHED.getCode());

        db.entityMetadataDao().insert(entityMetadata);
        getActionEmitter().postValue(DrugResistantTbEnrollmentActivity.Action.EDIT_PATIENT);
    }


    public void onError(Throwable throwable){

    }
}