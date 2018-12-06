package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel;

import android.app.Application;

import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.InjectableViewModel;

public class PatientDashboardViewModel extends BaseAndroidViewModel implements InjectableViewModel {

    private Client mClient;


    public PatientDashboardViewModel(Application application){
        super(application);

    }
}
