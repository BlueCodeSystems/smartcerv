package zm.gov.moh.common.submodule.registration.viewmodel;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.registration.model.RegistrationFormData;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.entity.domain.Patient;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.InjectorUtils;

public class RegistrationViewModel extends BaseAndroidViewModel implements InjectableViewModel {

   private Map<Integer, String> gender;
   private boolean isFormValid = false;
   private Repository repository;
   private MutableLiveData<Boolean> validateAndSubmitFormObserver;
   private MutableLiveData<Long> clientDashBoardTransition;

    final long SESSION_LOCATION_ID = getRepository().getDefaultSharePrefrences()
            .getLong(getApplication().getResources().getString(zm.gov.moh.core.R.string.session_location_key), 1);

    private RegistrationFormData registrationFormData;

    public RegistrationViewModel(Application application){
        super(application);

         int male = R.id.radio_male;
         int female = R.id.radio_female;

        gender = new HashMap<>();
        gender.put(male,"M");
        gender.put(female,"F");
        registrationFormData = new RegistrationFormData();
        registrationFormData.setGender(gender.get(female));
        InjectorUtils.provideRepository(this, application);
    }

    public void validateForm(){

        validateAndSubmitFormObserver.setValue(true);
    }

    public void submitForm(){

        getRepository().asyncFunction(this::persistData,clientDashBoardTransition::setValue,registrationFormData,this::onError);
    }

    public long persistData(RegistrationFormData registrationFormData){

        long  id = DatabaseUtils.generateLocalId(getRepository().getDatabase().personDao()::getMaxId);

        long  patientIdentifierId = DatabaseUtils.generateLocalId(getRepository().getDatabase().patientIdentifierDao()::getMaxId);

        String d = registrationFormData.getDateOfBirth().toString()+"T12:00:00Z";

        LocalDateTime dateOfBirth = ZonedDateTime.parse( d,DateTimeFormatter.ISO_ZONED_DATE_TIME).toLocalDateTime();

        String gender = registrationFormData.getGender().toString();

        String firstName = registrationFormData.getFirstName().toString();

        String lastName = registrationFormData.getLastName().toString();

        String address1 = registrationFormData.getAddress1().toString();

        String district = registrationFormData.getDistrict().toString();

        String province = registrationFormData.getProvince().toString();

        //getPersons
        Person person = new Person(id, dateOfBirth, gender);

        //getPersons name
        PersonName personName = new PersonName(id,firstName,lastName, preffered());

        //getPersons address
        PersonAddress personAddress = new PersonAddress(id, address1, district, province, preffered());

        //getPersons address
        Patient patient = new Patient(id, LocalDateTime.now());

        PatientIdentifier ccpiz = new PatientIdentifier(patientIdentifierId, id, String.valueOf(id).substring(14), 3, preffered(),SESSION_LOCATION_ID,LocalDateTime.now());

        //persist database entity instances asynchronously into the database
        getRepository().consumeAsync(getRepository().getDatabase().patientIdentifierDao()::insert,this::onError, ccpiz);

        getRepository().consumeAsync(getRepository().getDatabase().personDao()::insert,this::onError, person);

        getRepository().consumeAsync(getRepository().getDatabase().personNameDao()::insert,this::onError, personName);

        getRepository().consumeAsync(getRepository().getDatabase().personAddressDao()::insert,this::onError, personAddress);

        getRepository().consumeAsync(getRepository().getDatabase().patientDao()::insert,this::onError, patient);

        return id;
    }

    public void onGenderRadioGroupCheckedChange(int buttonId){

        registrationFormData.setGender(gender.get(buttonId));
    }

    public void onDateOfBirthChanged(String date){

        registrationFormData.setDateOfBirth(date);
    }

    public MutableLiveData<Boolean> getValidateAndSubmitFormObserver() {

        if(validateAndSubmitFormObserver == null)
            validateAndSubmitFormObserver = new MutableLiveData<>();

        return validateAndSubmitFormObserver;
    }

    public void setFormValid(boolean formValid) {
        isFormValid = formValid;
    }

    public boolean getFormValid() {
        return isFormValid;
    }

    public RegistrationFormData getRegistrationFormData() {
        return registrationFormData;
    }

    public MutableLiveData<Long> getClientDashBoardTransition() {

        if(clientDashBoardTransition == null)
            clientDashBoardTransition = new MutableLiveData<>();

        return clientDashBoardTransition;
    }

    public void onError(Throwable throwable){

    }
}
