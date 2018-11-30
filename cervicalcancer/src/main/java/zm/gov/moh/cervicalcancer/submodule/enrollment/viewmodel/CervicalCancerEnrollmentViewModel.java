package zm.gov.moh.cervicalcancer.submodule.enrollment.viewmodel;

import android.app.Application;

import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Random;

import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.entity.domain.Patient;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.utils.BaseAndroidViewModel;

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
    private final long CERVICAL_CANCER_ID_TYPE = 4;
    private final long OPENMRS_ID_TYPE = 3;


    public CervicalCancerEnrollmentViewModel(Application application){
        super(application);

    }

    public void enrollPatient(HashMap<String,Object> formData){

        getRepository().consumeAsync(this::newClient, this::onError, formData);
    }

    public void newClient(HashMap<String,Object> formData){

        //TODO:Replace with IDs generated from OPENMRS
        Random rand = new Random();
        long  id = rand.nextInt(100000) + 100000;

        long  personId = DatabaseUtils.generateLocalId(getRepository().getDatabase().personDao()::getMaxId);

        long  personNameId = DatabaseUtils.generateLocalId(getRepository().getDatabase().personNameDao()::getMaxId);

        long  personAddressId = DatabaseUtils.generateLocalId(getRepository().getDatabase().personAddressDao()::getMaxId);

        long  patientIdentifierIdOpenmrs = DatabaseUtils.generateLocalId(getRepository().getDatabase().patientIdentifierDao()::getMaxId);

        long  patientIdentifierIdccpiz = patientIdentifierIdOpenmrs + 1;

        String middayZonedTime = getApplication().getResources().getString(zm.gov.moh.common.R.string.zoned_time_mid_day);

        ZonedDateTime dateOfBirth =  ZonedDateTime.parse((String)formData.get(DOB_TAG) + middayZonedTime,
                DateTimeFormatter.ISO_ZONED_DATE_TIME);

        //patient id Openmrs
        PatientIdentifier patientIdentifier = new PatientIdentifier(patientIdentifierIdOpenmrs, personId,
                String.valueOf(id),
                OPENMRS_ID_TYPE,
                (long)formData.get(FACILITY_TAG),ZonedDateTime.now());

        //patient id CCPIZ
        PatientIdentifier ccpiz = new PatientIdentifier(patientIdentifierIdccpiz, personId,
                (String)formData.get(CLIENT_ID_TAG),
                CERVICAL_CANCER_ID_TYPE, preffered(),
                (long)formData.get(FACILITY_TAG),ZonedDateTime.now());

        //getPersons
        Person person = new Person(personId, dateOfBirth, GENDER);

        Patient patient = new Patient(personId, ZonedDateTime.now());

        //getPersons name
        PersonName personName = new PersonName(personNameId,personId,(String)formData.get(FIRST_NAME_TAG),
                (String)formData.get(LAST_NAME_TAG),
                preffered());

        //getPersons address
        PersonAddress personAddress = new PersonAddress(personAddressId, personId,
                (String) formData.get(ADDRESS_TAG),
                null,null, preffered());


        //persist data
        getRepository().consumeAsync(getRepository().getDatabase().patientIdentifierDao()::insert,this::onError, ccpiz);
        getRepository().consumeAsync(getRepository().getDatabase().patientIdentifierDao()::insert,this::onError, patientIdentifier);
        getRepository().consumeAsync(getRepository().getDatabase().personDao()::insert,this::onError, person);
        getRepository().consumeAsync(getRepository().getDatabase().patientDao()::insert,this::onError, patient);
        getRepository().consumeAsync(getRepository().getDatabase().personNameDao()::insert,this::onError, personName);
        getRepository().consumeAsync(getRepository().getDatabase().personAddressDao()::insert,this::onError, personAddress);
    }

    public void exitingClient(HashMap<String,Object> formData){

    }

    public void onError(Throwable throwable){

    }
}