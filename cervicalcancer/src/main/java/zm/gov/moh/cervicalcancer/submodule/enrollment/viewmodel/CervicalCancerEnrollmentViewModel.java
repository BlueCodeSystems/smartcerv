package zm.gov.moh.cervicalcancer.submodule.enrollment.viewmodel;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;


import java.util.List;

import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import zm.gov.moh.cervicalcancer.OpenmrsConfig;
import zm.gov.moh.cervicalcancer.submodule.enrollment.view.CervicalCancerEnrollmentActivity;
import zm.gov.moh.core.Constant;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.system.EntityMetadata;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.worker.RemoteWorker;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class CervicalCancerEnrollmentViewModel extends BaseAndroidViewModel {

    private MutableLiveData<String>actionEmitter;

    //TODO:Must read value from global context
    private final long CERVICAL_CANCER_ID_TYPE = 4;
    private final long OPENMRS_ID_TYPE = 3;


    public CervicalCancerEnrollmentViewModel(Application application){
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

        //TODO:Replace with IDs generated from OPENMRS

        long  personId = bundle.getLong(Key.PERSON_ID);
        long locationId = (long) bundle.get(Key.LOCATION_ID);
        String identifier = (String) bundle.get(Key.PATIENT_ID);
        String action = bundle.getString(Key.ACTION);
        long patientIdentifierIdccpiz = DatabaseUtils.generateLocalId(getRepository().getDatabase().patientIdentifierDao()::getMaxId);

        PatientIdentifierEntity patientIdentifier =  db.patientIdentifierDao().getByLocationType(personId,locationId, OpenmrsConfig.IDENTIFIER_TYPE_CCPIZ_UUID);

            if(patientIdentifier == null) {


                //Create database entity instances

                //patient id CCPIZ
                patientIdentifier = new PatientIdentifierEntity(patientIdentifierIdccpiz, personId,
                        identifier,
                        CERVICAL_CANCER_ID_TYPE, preffered(),
                        locationId, LocalDateTime.now());
            }else {
                patientIdentifier.setIdentifier(identifier);
                ConcurrencyUtils.consumeAsync(getRepository().getDatabase().patientIdentifierDao()::insert, this::onError, patientIdentifier);
                patientIdentifier.setDateCreated(LocalDateTime.now());
                patientIdentifier.setDateChanged(LocalDateTime.now());
                patientIdentifier.setPatientIdentifierId(patientIdentifierIdccpiz);
            }

            //persist database entity instances asynchronously into the database
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().patientIdentifierDao()::insert, this::onError, patientIdentifier);
            if(action != null && action.equals(CervicalCancerEnrollmentActivity.Action.ENROLL_PATIENT))
                getActionEmitter().postValue(CervicalCancerEnrollmentActivity.Action.ENROLL_PATIENT);

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
        getActionEmitter().postValue(CervicalCancerEnrollmentActivity.Action.EDIT_PATIENT);

    }


    public void onError(Throwable throwable){

    }
}