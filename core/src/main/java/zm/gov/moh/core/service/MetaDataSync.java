package zm.gov.moh.core.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.jakewharton.threetenabp.AndroidThreeTen;

import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.api.rest.RestApi;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.repository.database.entity.domain.Patient;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierType;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.Provider;
import zm.gov.moh.core.repository.database.entity.domain.User;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.InjectorUtils;

public class MetaDataSync extends IntentService implements InjectableViewModel {

    private Repository repository;
    private String token = "";
    public MetaDataSync(){
        super("");


    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        AndroidThreeTen.init(this);
        InjectorUtils.provideRepository(this,getApplication());
       RestApi restApi =  repository.getRestApi(this.token);

       restApi.location(this::putLocations, this::onError);
       restApi.user(this::putUsers, this::onError);
       restApi.personName(this::putPersonNames, this::onError);
       restApi.person(this::putPersons, this::onError);
       restApi.provider(this::putProviders, this::onError);

        restApi.patient(this::putPatients, this::onError);
        restApi.patientIdentifier(this::putPatientIdentifiers, this::onError);
        restApi.patientIdentifierType(this::putPatientIdentifierType, this::onError);
        restApi.personAddress(this::putPersonAddresses, this::onError);
    }

    @Override
    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public void putLocations(Location... locations){

        if(locations.length > 0)
            repository.insertEntityAsync(repository.getDatabase().locationDao()::insert, locations);
    }

    public void putUsers(User... users){

        if(users.length > 0)
            repository.insertEntityAsync(repository.getDatabase().userDao()::insert, users);
    }

    public void putProviders(Provider... providers){

        if(providers.length > 0)
            repository.insertEntityAsync(repository.getDatabase().providerDao()::insert, providers);
    }

    public void putPersonNames(PersonName... personNames){

        if(personNames.length > 0)
            repository.insertEntityAsync(repository.getDatabase().personNameDao()::insert, personNames);
    }

    public void putPersonAddresses(PersonAddress... personAddresses){

        if(personAddresses.length > 0)
            repository.insertEntityAsync(repository.getDatabase().personAddressDao()::insert, personAddresses);
    }

    public void putPersons(Person... persons){

        if(persons.length > 0)
            repository.insertEntityAsync(repository.getDatabase().personDao()::insert, persons);
    }

    public void putPatients(Patient... patients){

        if(patients.length > 0)
            repository.insertEntityAsync(repository.getDatabase().patientDao()::insert, patients);
    }

    public void putPatientIdentifiers(PatientIdentifier... patientIdentifiers){

        if(patientIdentifiers.length > 0)
            repository.insertEntityAsync(repository.getDatabase().patientIdentifierDao()::insert, patientIdentifiers);
    }

    public void putPatientIdentifierType(PatientIdentifierType... patientIdentifierTypes){

        if(patientIdentifierTypes.length > 0)
            repository.insertEntityAsync(repository.getDatabase().patientIdentifierTypeDao()::insert, patientIdentifierTypes);
    }

    public void onError(Throwable throwable){
        Exception e = new Exception(throwable);
    }
}
