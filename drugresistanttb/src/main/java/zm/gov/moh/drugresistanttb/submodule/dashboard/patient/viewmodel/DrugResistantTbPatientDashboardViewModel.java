package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.viewmodel;


import android.app.Application;
import android.os.Bundle;

import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.utils.BaseAndroidViewModel;

public class DrugResistantTbPatientDashboardViewModel extends BaseAndroidViewModel {
    private Bundle bundle;
    long person_id;

    private VisitDao visitDao = getRepository().getDatabase().visitDao();
    private Database db = getRepository().getDatabase();

    public DrugResistantTbPatientDashboardViewModel(Application application) {
        super(application);
    }

    public Repository getRepository() {
        return mRepository;
    }

    public void setRepository(Repository repository) {
        mRepository = repository;
    }

    @Override
    public Bundle getBundle() {
        return bundle;
    }

    @Override
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;

        person_id = (Long) bundle.get(Key.PERSON_ID);
    }

    public long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(long person_id) {
        this.person_id = person_id;
    }
}
