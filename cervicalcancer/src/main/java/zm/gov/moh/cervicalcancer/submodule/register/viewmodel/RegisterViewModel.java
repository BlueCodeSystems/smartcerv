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

    private MutableLiveData<List<Client>> clientsList;
    long facilityLocationId;
    private String identifierTypeUuid;

    public RegisterViewModel(Application application){
        super(application);

        facilityLocationId = mRepository.getDefaultSharePrefrences().getLong(Key.LOCATION_ID, 1);
    }

    public void getClients(BiFunction<List<Client>, List<Client>, List<Client>> sorter){

        this.getClientsSearch(sorter, new LinkedList<>());
    }

    private void getClientsSearch(BiFunction<List<Client>, List<Client>, List<Client>> sorter, List<Long> filterIds){

        try {

            Long identifierTypeId = db.patientIdentifierTypeDao().findIdByUuid(identifierTypeUuid);

            if(identifierTypeId != null){

                List<Client> inPatient = (filterIds.size() > 0)? db.clientDao().findClientsByIdentifierTypeLocation(facilityLocationId, identifierTypeId, filterIds):
                        db.clientDao().findClientsByIdentifierTypeLocation(facilityLocationId, identifierTypeId);

                List<Client> outPatient = (filterIds.size() > 0)? db.clientDao().getAllClientsNotEnrolledByLocation(facilityLocationId, identifierTypeId, filterIds):
                        db.clientDao().getAllClientsNotEnrolledByLocation(facilityLocationId, identifierTypeId);

                final List<Client>clientList = sorter.apply(inPatient, outPatient);

                getClientsList().postValue(clientList);
            }else
                throw new Exception("Identifier type not found");

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public MutableLiveData<List<Client>> getClientsList() {

        if(clientsList == null)
            clientsList = new MutableLiveData<>();

        return clientsList;
    }

    private List<Client> sorter(List<Client> inPatients, List<Client> outPatients){

        List<Client> patients = new LinkedList<>();
        patients.addAll(inPatients);
        patients.add(new Client());
        patients.addAll(outPatients);

        return patients;
    }

    public void setIdentifierTypeUuid(String identifierTypeUuid) {
        this.identifierTypeUuid = identifierTypeUuid;
    }

    public void getAllClients(){
        ConcurrencyUtils.consumeAsync(this::getClients, this::onError, this::sorter);
    }

    public void setSearchArguments(List<Long> args) {

        ConcurrencyUtils.biConsumeAsync(this::getClientsSearch, this::onError, this::sorter, args);
    }
}
