package zm.gov.moh.drugresistanttb.submodule.register.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.utils.BaseAndroidViewModel;

public class DrugResistantTbRegisterViewModel extends BaseAndroidViewModel{

    private LiveData<List<Client>> allClients;
    long facilityLocationId;

    public DrugResistantTbRegisterViewModel(Application application){
        super(application);


        facilityLocationId = mRepository.getDefaultSharePrefrences()
                .getLong(Key.LOCATION_ID, 1);

        allClients = db.genericDao().getAllPatientsByLocation(facilityLocationId);
    }

    public LiveData<List<Client>> getAllClients() {

        return allClients;
    }

    public LiveData<List<Client>> getMatchedClients(List<Long> ids) {

        return db.genericDao().getPatientsByLocation(facilityLocationId, ids);
    }
}
