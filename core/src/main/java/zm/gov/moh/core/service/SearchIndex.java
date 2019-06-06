package zm.gov.moh.core.service;

import android.app.IntentService;
import android.content.Intent;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;

import java.util.List;

import androidx.annotation.Nullable;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.repository.database.entity.fts.ClientNameFts;
import zm.gov.moh.core.utils.ConcurrencyUtils;
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

        ConcurrencyUtils.consumeAsync(
                clients -> {

                    for(Client client: clients) {

                        String age = String.valueOf(LocalDate.now().getYear() - client.getBirthDate().getYear());
                        String gender = (client.getGender().equals("F"))? "Female":"Male";
                        String searchTerm = DatabaseUtils.buildSearchTerm(age,client.getIdentifier(),client.getGivenName(),client.getFamilyName(),gender);

                        repository.getDatabase().clientFtsDao().
                                insert(new ClientNameFts(client.getPatientId(), searchTerm, client.getPatientId()));

                       // List<Long> list = repository.getDatabase().clientFtsDao().findClientByTerms("zita");
                       // list.get(0);
                    }
                }, //consumer
                 this::onError,
                repository.getDatabase().clientDao().getAllClients());
    }

    @Override
    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Repository getRepository() {
        return repository;
    }

    public void onError(Throwable throwable){
        Exception e = new Exception(throwable);
    }
}