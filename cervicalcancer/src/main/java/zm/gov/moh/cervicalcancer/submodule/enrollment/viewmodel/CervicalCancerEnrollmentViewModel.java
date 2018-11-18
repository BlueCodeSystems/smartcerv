package zm.gov.moh.cervicalcancer.submodule.enrollment.viewmodel;

import android.app.Application;

import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Random;

import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.utils.BaseAndroidViewModel;

public class CervicalCancerEnrollmentViewModel extends BaseAndroidViewModel {

    private final String GENDER = "Female";
    private final String FIRST_NAME_TAG = "first name";
    private final String LAST_NAME_TAG = "last name";
    private final String CLIENT_ID_NAME_TAG = "client ID";
    private final String ADDRESS_TAG = "address";
    private final String PHONE_TAG = "phone";
    private final String DISTRICT_TAG = "district";
    private final String FACILITY_TAG = "facility";
    private final String NRC_TAG = "nrc";
    private final String DOB_TAG = "dob";
    private final String VIA_SCREENING_DATE_TAG = "via screening date";

    public CervicalCancerEnrollmentViewModel(Application application){
        super(application);

    }

    public void enrollPatient(HashMap<String,Object> formData){

        newClient(formData);
    }

    public void newClient(HashMap<String,Object> formData){

        Random rand = new Random();

        long  id = rand.nextInt(500) + 100;

        //person
        Person person = new Person(id,
                ZonedDateTime.parse((String)formData.get(DOB_TAG), DateTimeFormatter.ofPattern("dd-MM-yyyy")), GENDER);

        //person name
        PersonName personName = new PersonName(id,
                (String)formData.get(FIRST_NAME_TAG),
                (String)formData.get(LAST_NAME_TAG), preffered());

        //person address
        PersonAddress personAddress = new PersonAddress(id,
                (String) formData.get(ADDRESS_TAG),
                (String) formData.get(DISTRICT_TAG),null, preffered());

        //persist data
        getRepository().insertEntityAsync(getRepository().getDatabase().personDao()::insert, person);
        getRepository().insertEntityAsync(getRepository().getDatabase().personNameDao()::insert, personName);
        getRepository().insertEntityAsync(getRepository().getDatabase().personAddressDao()::insert, personAddress);
    }

    public void exitingClient(HashMap<String,Object> formData){

    }
}