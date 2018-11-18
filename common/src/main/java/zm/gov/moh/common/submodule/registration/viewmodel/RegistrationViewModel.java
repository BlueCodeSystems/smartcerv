package zm.gov.moh.common.submodule.registration.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.registration.model.RegistrationFormData;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.domain.Patient;
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

    private RegistrationFormData registrationFormData;

    public RegistrationViewModel(Application application){
        super(application);

         int male = R.id.radio_male;
         int female = R.id.radio_female;

        gender = new HashMap<>();
        gender.put(male,"Male");
        gender.put(female,"Female");
        registrationFormData = new RegistrationFormData();
        registrationFormData.setGender(gender.get(female));
        InjectorUtils.provideRepository(this, application);
    }

    public void validateForm(){

        validateAndSubmitFormObserver.setValue(true);
    }

    public void submitForm(){

        Random rand = new Random();

        long  id = rand.nextInt(500) + 100;

        String d = registrationFormData.getDateOfBirth().toString()+"T12:00:00Z";

        ZonedDateTime dateOfBirth = ZonedDateTime.parse( d,DateTimeFormatter.ISO_ZONED_DATE_TIME);

        String gender = registrationFormData.getGender().toString();

        String firstName = registrationFormData.getFirstName().toString();

        String lastName = registrationFormData.getLastName().toString();

        String address1 = registrationFormData.getAddress1().toString();

        String district = registrationFormData.getDistrict().toString();

        String province = registrationFormData.getProvince().toString();

        //person
        Person person = new Person(id, dateOfBirth, gender);

        //person name
        PersonName personName = new PersonName(id,firstName,lastName, preffered());

        //person address
        PersonAddress personAddress = new PersonAddress(id, address1, district, province, preffered());

        //person address
        Patient patient = new Patient(id, ZonedDateTime.now());

        repository.insertEntityAsync(repository.getDatabase().personDao()::insert, person);

        repository.insertEntityAsync(repository.getDatabase().personNameDao()::insert, personName);

        repository.insertEntityAsync(repository.getDatabase().personAddressDao()::insert, personAddress);

        repository.insertEntityAsync(repository.getDatabase().patientDao()::insert, patient);

        clientDashBoardTransition.setValue(id);
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

    @Override
    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<Long> getClientDashBoardTransition() {

        if(clientDashBoardTransition == null)
            clientDashBoardTransition = new MutableLiveData<>();

        return clientDashBoardTransition;
    }
}
