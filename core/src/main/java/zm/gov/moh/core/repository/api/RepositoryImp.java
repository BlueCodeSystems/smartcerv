package zm.gov.moh.core.repository.api;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.function.Function;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zm.gov.moh.core.repository.api.rest.RestApi;
import zm.gov.moh.core.repository.api.rest.RestApiImpl;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.dao.domain.PersonAddressDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAttributeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAttributeTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonNameDao;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.utils.InjectorUtils;

public class RepositoryImp implements Repository{

    private RestApi restapi;
    private Database database;

    public RepositoryImp(Application application){

        this.database = Database.getDatabase(application);
    }

    @Override
    public RestApi getRestApi(String accessToken) {

        if(restapi == null || (!restapi.getAccessToken().equals(accessToken)))
            synchronized (RepositoryImp.class){

                if(restapi == null)
                    restapi = new RestApiImpl(InjectorUtils.provideRestAPIAdapter(), accessToken);
            }
        else
            return restapi;

        return restapi;
    }

    public PersonDao getPersonDao(){

        return database.personDao();
    }

    public PersonNameDao getPersonNameDao(){

        return database.personNameDao();
    }

    public PersonAddressDao getPersonAddressDao(){

        return database.personAddressDao();
    }

    public PersonAttributeDao getPersonAttributeDao(){

        return database.personAttributeDao();
    }
    public PersonAttributeTypeDao getPersonAttributeTypeDao(){

        return database.personAttributeTypeDao();
    }

    public LiveData<List<Client>> getAllClients(){

        return database.clientDao().findAllClients();
    }

    public Client getClientById(long id){
        return null;
    }

    public void insertPerson(Person person){

        Single.just(person)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(person1 -> {

                    getPersonDao().insert(person1);

                    return person1;
                })
                .subscribe(person1 -> {

                    getPersonDao().insert(person1);

                },throwable -> {
                    new Exception(throwable);
                });
    }

    public void insertPersonName(PersonName personName){

        Single.just(personName)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(personName1 -> {

                    getPersonNameDao().insert(personName1);

                });
    }

    public void insertPersonAdress(PersonAddress personAddress){

        Single.just(personAddress)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(personAddress1 -> {

                    getPersonAddressDao().insert(personAddress1);

                });
    }

    public LiveData<List<Person>> getAllPeople(){

        return database.personDao().getAll();
    }

    public LiveData<List<Client>> findClientByTerm(String term){

        return database.clientDao().findByTerm(term);
    }
}