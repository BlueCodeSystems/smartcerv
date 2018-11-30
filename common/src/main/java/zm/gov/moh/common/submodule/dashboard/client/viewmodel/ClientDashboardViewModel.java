package zm.gov.moh.common.submodule.dashboard.client.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.InjectorUtils;

public class ClientDashboardViewModel extends BaseAndroidViewModel implements InjectableViewModel {

    private Client mClient;


    public ClientDashboardViewModel(Application application){
        super(application);

    }
}
