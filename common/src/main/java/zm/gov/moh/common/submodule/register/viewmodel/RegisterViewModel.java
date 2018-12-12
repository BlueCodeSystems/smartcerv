package zm.gov.moh.common.submodule.register.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.InjectorUtils;

public class RegisterViewModel extends BaseAndroidViewModel implements InjectableViewModel {

    private LiveData<List<Client>> allClients;
    private LiveData<List<Long>> searchTerm;
    private Repository repository;

    public RegisterViewModel(Application application){
        super(application);


        InjectorUtils.provideRepository(this, application);

        getRepository().getDatabase().clientDao().findAllClients();
       // searchClientsId = repository.getDatabase().clientFtsDao().
    }

    public LiveData<List<Client>> getAllClients() {

        return allClients;
    }

    public void setSearchTerm(String term){


    }
}
