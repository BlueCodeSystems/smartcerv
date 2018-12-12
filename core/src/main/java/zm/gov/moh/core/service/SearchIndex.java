package zm.gov.moh.core.service;

import android.app.IntentService;
import android.content.Intent;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;

import androidx.annotation.Nullable;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.repository.database.entity.fts.ClientNameFts;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.InjectorUtils;

public class SearchIndex extends IntentService implements InjectableViewModel {

    private Repository repository;

    public SearchIndex(){
        super("");


    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        AndroidThreeTen.init(this);
        InjectorUtils.provideRepository(this, getApplication());

        repository.consumeAsync(
                clients -> {

                    for(Client client: clients) {
                        String age = String.valueOf(LocalDate.now().getYear() - client.birthdate.getYear());
                        String gender = (client.gender.equals("F"))? "Female":"Male";
                        String searchTerm = DatabaseUtils.buildSearchTerm(age,client.identifier,client.given_name,client.family_name,gender);

                        repository.getDatabase().clientFtsDao().
                                insert(new ClientNameFts(client.patient_id, searchTerm, client.patient_id));
                    }
                }, //consumer
                 this::onError,
                repository.getDatabase().clientDao().getAllClients());
    }

    @Override
    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public void onError(Throwable throwable){
        Exception e = new Exception(throwable);
    }
}