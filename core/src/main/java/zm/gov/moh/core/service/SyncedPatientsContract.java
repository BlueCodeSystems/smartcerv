package zm.gov.moh.core.service;

import java.util.List;

import androidx.annotation.NonNull;
import zm.gov.moh.core.model.Patient;

public interface SyncedPatientsContract {
    interface View extends BaseView<Presenter> {
        void updateAdapter(List<Patient> patientList);

        void updateListVisibility(boolean isVisible);

        void updateListVisibility(boolean isVisible, @NonNull String replacementWord);
    }

    interface Presenter extends BasePresenterContract {
        void setQuery(String query);

        void updateLocalPatientsList();
    }
}
