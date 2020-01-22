package zm.gov.moh.cervicalcancer.submodule.register.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.LinkedList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.functions.BiFunction;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class RegisterViewModel extends BaseAndroidViewModel{

    private LiveData<List<Client>> allClients;
    private MutableLiveData<List<Client>> clientsList;
    long facilityLocationId;

    public RegisterViewModel(Application application){
        super(application);


        facilityLocationId = mRepository.getDefaultSharePrefrences()
                .getLong(Key.LOCATION_ID, 1);

        allClients = db.genericDao().getAllPatientsByLocation(facilityLocationId);

        ConcurrencyUtils.consumeAsync(this::getClients, this::onError, this::sorter);
    }

    public LiveData<List<Client>> getAllClients() {

        return allClients;
    }

    public LiveData<List<Client>> getMatchedClients(List<Long> ids) {

        return db.genericDao().getPatientsByLocation(facilityLocationId, ids);
    }

    public void getClients(BiFunction<List<Client>, List<Client>, List<Client>> sorter){

        try {

            List<Client> inPatient =  db.clientDao().findClientsByIdentifierTypeLocation(facilityLocationId, 4L);
            List<Client> outPatient = db.clientDao().getAllClientsNotEnrolledByLocation(facilityLocationId, 4L);

            final List<Client>clientList = sorter.apply(inPatient, outPatient);

            clientsList.postValue(clientList);
        }catch (Exception ex){

        }
    }

    public MutableLiveData<List<Client>> getClientsList() {

        if(clientsList == null)
            clientsList = new MutableLiveData<>();

        return clientsList;
    }

    public List<Client> sorter(List<Client> inPatients, List<Client> outPatients){

        List<Client> patients = new LinkedList<>();
        Client partion = new Client();
        partion.setType(Client.Type.PARTITION);

        for(Client client: inPatients)
            client.setType(Client.Type.INPATIENT);

        for(Client client: outPatients)
            client.setType(Client.Type.OUTPATIENT);

        patients.addAll(inPatients);
        patients.add(partion);
        patients.addAll(outPatients);

        return patients;
    }
}
