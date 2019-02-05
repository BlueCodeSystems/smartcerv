package zm.gov.moh.cervicalcancer.submodule.register.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.utils.BaseAndroidViewModel;

public class RegisterViewModel extends BaseAndroidViewModel{

    private LiveData<List<Client>> allClients;
    private LiveData<List<Client>> searchClients;
    private Repository repository;

    public RegisterViewModel(Application application){
        super(application);


        long facilityLocationId = repository.getDefaultSharePrefrences()
                .getLong(application.getResources().getString(zm.gov.moh.core.R.string.session_location_key), 1);

        allClients = repository.getDatabase().genericDao().getAllPatientsByLocation(facilityLocationId);
    }

    @Override
    public void setRepository(Repository repository) {

        this.repository = repository;
    }

    public LiveData<List<Client>> getAllClients() {

        return allClients;
    }
}
