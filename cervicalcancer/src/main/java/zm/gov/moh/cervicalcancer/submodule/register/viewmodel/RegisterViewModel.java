package zm.gov.moh.cervicalcancer.submodule.register.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.utils.BaseAndroidViewModel;

public class RegisterViewModel extends BaseAndroidViewModel{

    private LiveData<List<Client>> allClients;

    public RegisterViewModel(Application application){
        super(application);


        long facilityLocationId = mRepository.getDefaultSharePrefrences()
                .getLong(Key.LOCATION_ID, 1);

        allClients = db.genericDao().getAllPatientsByLocation(facilityLocationId);
    }

    public LiveData<List<Client>> getAllClients() {

        return allClients;
    }
}
