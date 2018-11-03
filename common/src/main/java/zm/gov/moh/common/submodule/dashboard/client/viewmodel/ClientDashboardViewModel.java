package zm.gov.moh.common.submodule.dashboard.client.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.InjectorUtils;

public class ClientDashboardViewModel extends AndroidViewModel implements InjectableViewModel {

    private Client mClient;
    private Repository mRepository;


    ClientDashboardViewModel(Application application){
        super(application);

        InjectorUtils.provideRepository(this, application);

    }

    @Override
    public void setRepository(Repository repository) {

        this.mRepository = repository;
    }

    public LiveData<Client> getClientById(long id){

        return mRepository.getClientById(id);
    }

    public LiveData<PersonAddress> getPersonAddressByPersonId(long id){

        return mRepository.getPersonAddressByPersonId(id);
    }
}
