package zm.gov.moh.core.service;

import android.app.IntentService;
import android.content.Intent;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.Nullable;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
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
        ConcurrencyUtils.asyncRunnable(this::indexSearch,this::onError);
    }

    public void indexSearch(){

        long locationId = repository.getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0);
        repository.getDatabase().clientFtsDao().clear();

        List<PatientIdentifierEntity> patientIdentifierEntities= repository.getDatabase().patientIdentifierDao().getByLocationId(locationId);
        Set<Long> personIds = new HashSet<> ();

        if(patientIdentifierEntities.size() > 0)
            for(PatientIdentifierEntity patientIdentifierEntity: patientIdentifierEntities){
                if(patientIdentifierEntity != null) {
                    personIds.add(patientIdentifierEntity.getPatientId());
                    String term = null;
                    if(patientIdentifierEntity.getIdentifierType() == 3)
                        term = DatabaseUtils.buildSearchTerm(patientIdentifierEntity.getIdentifier());

                    if(patientIdentifierEntity.getIdentifierType() == 4) {

                        int index = patientIdentifierEntity.getIdentifier().lastIndexOf('-');
                        term = DatabaseUtils.buildSearchTerm(patientIdentifierEntity.getIdentifier().substring(index + 1));
                    }

                    repository.getDatabase().clientFtsDao().
                            insert(new ClientNameFts(term,patientIdentifierEntity.getPatientId()));
                }
            }

        List<PersonName> personNames = repository.getDatabase().personNameDao().findPersonNameById(personIds);

       if(personNames.size() > 0)
            for(PersonName personName: personNames){
                if(personName != null)
                    repository.getDatabase().clientFtsDao().
                            insert(new ClientNameFts(
                                    DatabaseUtils.buildSearchTerm(personName.getGivenName(),personName.getFamilyName()),
                                    personName.getPersonId()));
            }


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