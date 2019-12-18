package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.viewmodel;


import android.app.Application;
import android.os.Bundle;

import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.InjectableViewModel;

public class DrugResistantTbPatientDashboardViewModel extends BaseAndroidViewModel implements InjectableViewModel {
    protected Repository mRepository;
    private Bundle bundle;
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
}
