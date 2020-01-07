package zm.gov.moh.drugresistanttb.submodule.enrollment.viewModel;

import android.app.Application;
import android.os.Bundle;

import org.threeten.bp.LocalDateTime;

import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.ConcurrencyUtils;

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

    //TODO:Must read value from global context
    private final long DR_TB_ID_TYPE = 6;
    public DrugResistantTbEnrollmentViewModel(Application application) {
        super(application);
    }

    public void enrollPatient(Bundle bundle) {
        ConcurrencyUtils.consumeAsync(this::enrollClient, this::onError,bundle);
    }

    public void enrollClient(Bundle bundle) {

        //TODO:Replace with IDs generated from OPENMRS
        long personId = bundle.getLong(Key.PERSON_ID);

        long patientIdentifierIddrtbiz = DatabaseUtils.generateLocalId(getRepository().getDatabase().patientIdentifierDao()::getMaxId);

        //patient id DRTBIZ (Drug Resistant TB Identification Number -Zambia)
        PatientIdentifierEntity drtbiz = new PatientIdentifierEntity(patientIdentifierIddrtbiz, personId,
                (String) bundle.get(CLIENT_ID_TAG),
                DR_TB_ID_TYPE, preffered(),
                (long)bundle.get(FACILITY_TAG), LocalDateTime.now());

    }

    public void onError(Throwable throwable){

    }
}
