package zm.gov.moh.drugresistanttb.submodule.enrollment.viewModel;

import android.app.Application;
import android.os.Bundle;

import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class DrugResistantTbEnrollmentViewModel extends BaseAndroidViewModel {

    private final String FIRST_NAME = "first name";
    private final String LAST_NAME = "last name";
    private final String POPULAR_NAME = "popular name";
    private final String DR_TB_CASEID = "dr-tb case id";
    private final String DATE_OF_DIAGNOSIS = "date of diagnosis";
    private final String DATE_STARTED_ON_TREATMENT = "date started in treatment";
    private final String PHYSICAL_ADDRESS = "physical address";
    private final String DISTRICT = "district";
    private final String SEX = "sex";

    private final String DOB = "dob";

    public DrugResistantTbEnrollmentViewModel(Application application) {
        super(application);
    }

    public void enrollPatient(Bundle bundle) {
        ConcurrencyUtils.consumeAsync(this::enrollPatient, this::onError,bundle);
        //getRepository().consumeAsync(this::enrollClient, this::onError, bundle);
    }

    public void enrollClient(Bundle bundle) {

        //TODO:Replace with IDs generated from OPENMRS
        long personId = bundle.getLong(Key.PERSON_ID);
        //long  patientIndentifierIddrtb = 1;

    }

    public void onError(Throwable throwable){

    }
}
