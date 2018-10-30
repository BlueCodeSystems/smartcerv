package zm.gov.moh.common.submodule.registration.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.registration.model.RegistrationFormData;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.InjectorUtils;

public class RegistrationViewModel extends AndroidViewModel implements InjectableViewModel {

   private Map<Integer, String> gender;
   private boolean isFormValid = false;
   private Repository repository;
   private MutableLiveData<Boolean> validateAndSubmitFormObserver;

    private RegistrationFormData registrationFormData;

    RegistrationViewModel(Application application){
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

        int  id = rand.nextInt(500) + 100;


        LocalDate dateOfBirth = LocalDate.parse(registrationFormData.getDateOfBirth(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        String gender = registrationFormData.getGender().toString();

        //person
        Person person = new Person(id, dateOfBirth,gender);

        String firstName = registrationFormData.getFirstName().toString();

        String lastName = registrationFormData.getLastName().toString();

        //person name
        PersonName personName = new PersonName(id,firstName,lastName);

        //person address

        PersonAddress personAddress = new PersonAddress(id,registrationFormData.getAddress1().toString());

        repository.insertPerson(person);
        repository.insertPersonName(personName);
        repository.insertPersonAdress(personAddress);
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
}
