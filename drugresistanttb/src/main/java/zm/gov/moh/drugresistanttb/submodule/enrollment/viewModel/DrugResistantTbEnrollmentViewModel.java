package zm.gov.moh.drugresistanttb.submodule.enrollment.viewModel;

import android.app.Application;
import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import zm.gov.moh.drugresistanttb.OpenmrsConfig;
import zm.gov.moh.core.Constant;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.ConcurrencyUtils;
import zm.gov.moh.drugresistanttb.submodule.enrollment.view.DrugResistantTbEnrollmentActivity;

public class DrugResistantTbEnrollmentViewModel extends BaseAndroidViewModel {

    private final String FIRST_NAME = "first name";
    private final String LAST_NAME = "last name";
    final String CLIENT_ID_TAG = "client ID";
    private final String POPULAR_NAME = "popular name";
    private final String DR_TB_CASEID = "dr-tb case id";
    private final String DATE_OF_DIAGNOSIS = "date of diagnosis";
    private final String DATE_STARTED_ON_TREATMENT = "date started in treatment";
    private final String PHYSICAL_ADDRESS = "physical address";
    private final String DISTRICT = "district";
    private final String FACILITY_TAG = "facility";
    private final String SEX = "sex";
    private final String DOB = "dob";
    private MutableLiveData<String>actionEmitter;

    //TODO:Must read value from global context
    private final long DR_TB_ID_TYPE = 6;

    public DrugResistantTbEnrollmentViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<String> getActionEmitter() {
        if(actionEmitter == null)
            actionEmitter = new MutableLiveData<>();

        return actionEmitter;
    }

    public void enroll(Bundle bundle) {
        ConcurrencyUtils.consumeAsync(this::enrollClient, this::onError,bundle);
    }

    public void edit(Bundle bundle){

        ConcurrencyUtils.consumeAsync(this::editClient, this::onError, bundle);
    }

    public void enrollClient(Bundle bundle) {

        //TODO:Replace with IDs generated from OPENMRS
        long personId = bundle.getLong(Key.PERSON_ID);
        long locationId = (long) bundle.get(Key.LOCATION_ID);
        String identifier = (String) bundle.get(CLIENT_ID_TAG);

        PatientIdentifierEntity patientIdentifier =  db.patientIdentifierDao().getByLocationType(personId,locationId, OpenmrsConfig.IDENTIFIER_TYPE_MDRPIZ_UUID);

        if(patientIdentifier == null) {
            long patientIdentifierIddrtbiz = DatabaseUtils.generateLocalId(getRepository().getDatabase().patientIdentifierDao()::getMaxId);

            //patient id DRTBIZ (Drug Resistant TB Identification Number -Zambia)
            patientIdentifier = new PatientIdentifierEntity(patientIdentifierIddrtbiz, personId, identifier,
                    DR_TB_ID_TYPE, preffered(), (long) bundle.get(FACILITY_TAG), LocalDateTime.now());
        } else {
            patientIdentifier.setIdentifier(identifier);
            patientIdentifier.setDateChanged(LocalDateTime.now());
        }

        //persist database entity instances asynchronously into the database
        ConcurrencyUtils.consumeAsync(getRepository().getDatabase().patientIdentifierDao()::insert, this::onError, patientIdentifier);
        getActionEmitter().postValue(DrugResistantTbEnrollmentActivity.Action.ENROLL_PATIENT);
    }

    public void editClient(Bundle bundle){
        this.enrollClient(bundle);
        long  patientId = bundle.getLong(Key.PERSON_ID);
        Person person = getRepository().getDatabase().personDao().findById(patientId);

        if(person != null){
            person.setBirthDate(LocalDateTime.parse(bundle.getString(Key.PERSON_DOB)+ Constant.MID_DAY_TIME, DateTimeFormatter.ISO_ZONED_DATE_TIME));
            db.personDao().insert(person);
            db.clientDao().updateNamesById(patientId,bundle.getString(Key.PERSON_GIVEN_NAME),bundle.getString(Key.PERSON_FAMILY_NAME),LocalDateTime.now());
            db.clientDao().updatePhoneNumberByPatientId(patientId,bundle.getString(Key.PERSON_PHONE),LocalDateTime.now());
            db.clientDao().updateAddressById(patientId,bundle.getString(Key.PERSON_ADDRESS),LocalDateTime.now());
            db.personDao().updateNRCNumberBydID(patientId,bundle.getString(Key.NRC_NUMBER),LocalDateTime.now());
        }

        getActionEmitter().postValue(DrugResistantTbEnrollmentActivity.Action.EDIT_PATIENT);

    }

    public void onError(Throwable throwable){

    }
}
