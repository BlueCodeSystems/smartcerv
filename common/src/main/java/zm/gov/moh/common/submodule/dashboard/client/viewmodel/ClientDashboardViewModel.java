package zm.gov.moh.common.submodule.dashboard.client.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.InjectableViewModel;

public class ClientDashboardViewModel extends BaseAndroidViewModel implements InjectableViewModel {

    private Client mClient;


    public ClientDashboardViewModel(Application application){
        super(application);

    }
}
