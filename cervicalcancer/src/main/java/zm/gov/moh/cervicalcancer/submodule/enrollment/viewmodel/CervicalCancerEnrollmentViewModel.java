package zm.gov.moh.cervicalcancer.submodule.enrollment.viewmodel;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;


import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import zm.gov.moh.cervicalcancer.submodule.enrollment.view.CervicalCancerEnrollmentActivity;
import zm.gov.moh.core.Constant;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class CervicalCancerEnrollmentViewModel extends BaseAndroidViewModel {

    private final String GENDER = "F";
    private final String FIRST_NAME_TAG = "first name";
    private final String LAST_NAME_TAG = "last name";
    private final String CLIENT_ID_TAG = "client ID";
    private final String ADDRESS_TAG = "address";
    private final String PHONE_TAG = "phone";
    private final String DISTRICT_TAG = "district";
    private final String FACILITY_TAG = "facility";
    private final String NRC_TAG = "nrc";
    private final String DOB_TAG = "dob";
    private final String VIA_SCREENING_DATE_TAG = "via screening date";
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
            long patientIdentifierIdccpiz = DatabaseUtils.generateLocalId(getRepository().getDatabase().patientIdentifierDao()::getMaxId);

            //Create database entity instances

            //patient id CCPIZ
            PatientIdentifierEntity ccpiz = new PatientIdentifierEntity(patientIdentifierIdccpiz, personId,
                    (String) bundle.get(CLIENT_ID_TAG),
                    CERVICAL_CANCER_ID_TYPE, preffered(),
                    (long) bundle.get(Key.LOCATION_ID), LocalDateTime.now());

            //persist database entity instances asynchronously into the database
            ConcurrencyUtils.consumeAsync(getRepository().getDatabase().patientIdentifierDao()::insert, this::onError, ccpiz);
            getActionEmitter().postValue(CervicalCancerEnrollmentActivity.Action.ENROLL_PATIENT);

    }

    public void editClient(Bundle bundle){

        long  patientId = bundle.getLong(Key.PERSON_ID);
        Person person = getRepository().getDatabase().personDao().findById(patientId);

        if(person != null){
            person.setBirthDate(LocalDateTime.parse(bundle.getString(Key.PERSON_DOB)+ Constant.MID_DAY_TIME, DateTimeFormatter.ISO_ZONED_DATE_TIME));
            db.personDao().insert(person);
            db.clientDao().updateNamesById(patientId,bundle.getString(Key.PERSON_GIVEN_NAME),bundle.getString(Key.PERSON_FAMILY_NAME));
            db.clientDao().updatePhoneNumberByPatientId(patientId,bundle.getString(Key.PERSON_PHONE));
            db.clientDao().updateClientIdByPatientId(patientId,bundle.getString(Key.PERSON_ID));
            db.clientDao().updateAddressById(patientId,bundle.getString(Key.PERSON_ADDRESS));
        }

        getActionEmitter().postValue(CervicalCancerEnrollmentActivity.Action.EDIT_PATIENT);

    }


    public void onError(Throwable throwable){

    }
}