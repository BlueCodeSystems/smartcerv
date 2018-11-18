package zm.gov.moh.common.submodule.register.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.InjectorUtils;

public class RegisterViewModel extends AndroidViewModel implements InjectableViewModel {

    private LiveData<List<Client>> allClients;
    private LiveData<List<Client>> searchClients;
    private Repository repository;

    public RegisterViewModel(Application application){
        super(application);


        InjectorUtils.provideRepository(this, application);

        allClients = repository.getDatabase().clientDao().findAllClients();
    }

    @Override
    public void setRepository(Repository repository) {

        this.repository = repository;
    }

    public LiveData<List<Client>> getAllClients() {

        return allClients;
    }
}
