package zm.gov.moh.cervicalcancer.submodule.enrollment.viewmodel;

import android.app.Application;
import android.os.Bundle;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZonedDateTime;

import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.entity.domain.Patient;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifier;
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

    //TODO:Must read value from global context
    private final long CERVICAL_CANCER_ID_TYPE = 4;
    private final long OPENMRS_ID_TYPE = 3;


    public CervicalCancerEnrollmentViewModel(Application application){
        super(application);

    }

    public void enrollPatient(Bundle bundle){

        getRepository().consumeAsync(this::enrollClient, this::onError, bundle);
    }

    public void enrollClient(Bundle bundle){

        //TODO:Replace with IDs generated from OPENMRS

        long  personId = bundle.getLong(Key.PERSON_ID);

        long  patientIdentifierIdccpiz = DatabaseUtils.generateLocalId(getRepository().getDatabase().patientIdentifierDao()::getMaxId);

        //Create database entity instances

        //patient id CCPIZ
        PatientIdentifier ccpiz = new PatientIdentifier(patientIdentifierIdccpiz, personId,
                (String)bundle.get(CLIENT_ID_TAG),
                CERVICAL_CANCER_ID_TYPE, preffered(),
                (long)bundle.get(FACILITY_TAG),LocalDateTime.now());

        //persist database entity instances asynchronously into the database
        getRepository().consumeAsync(getRepository().getDatabase().patientIdentifierDao()::insert,this::onError, ccpiz);
    }


    public void onError(Throwable throwable){

    }
}