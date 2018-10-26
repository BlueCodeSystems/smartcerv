package zm.gov.moh.core.repository.api;

import android.app.Application;

import java.util.List;

import zm.gov.moh.core.repository.api.model.Client;
import zm.gov.moh.core.repository.api.rest.RestApi;
import zm.gov.moh.core.repository.api.rest.RestApiImpl;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.dao.domain.PersonAddressDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAttributeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAttributeTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonNameDao;
import zm.gov.moh.core.utils.InjectorUtils;

public class RepositoryImp implements Repository{

    private RestApi restapi;
    private Database database;

    public RepositoryImp(Application application){

        this.database = Database.getDatabase(application);
    }

    @Override
    public RestApi getRestApi(String accessToken) {

        if(restapi == null || (restapi.getAccessToken().equals(accessToken)))
            return restapi;
        else
            synchronized (RepositoryImp.class){

                if(restapi == null)
                    restapi = new RestApiImpl(InjectorUtils.provideRestAPIAdapter(), accessToken);
            }

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

    public List<Client> getAllClients(){
        return null;
    }

    public Client getClientById(long id){
        return null;
    }
}

/*
* package com.example.zita.architecturecomponent.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.zita.architecturecomponent.repository.database.AppDB;
import com.example.zita.architecturecomponent.repository.database.doa.PersonDoa;
import com.example.zita.architecturecomponent.repository.database.entity.Person;

import java.util.List;

public class AppRepository {

    LiveData<List<Person>> mAllpersons;
    private PersonDoa mPersonDoa;

    public AppRepository(Application application) {
        AppDB db = AppDB.getDB(application);
        mPersonDoa = db.personDoa();

        mAllpersons = mPersonDoa.getAllPersons();
    }

    public LiveData<List<Person>>getAllpersons(){
        return mAllpersons;
    }

    public void insertPerson(Person person){
        new insertAsyncTask(mPersonDoa).execute(person);
    }
package com.example.zita.architecturecomponent.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.zita.architecturecomponent.repository.database.AppDB;
import com.example.zita.architecturecomponent.repository.database.doa.PersonDoa;
import com.example.zita.architecturecomponent.repository.database.entity.Person;

import java.util.List;

public class AppRepository {

    LiveData<List<Person>> mAllpersons;
    private PersonDoa mPersonDoa;

    public AppRepository(Application application) {
        AppDB db = AppDB.getDB(application);
        mPersonDoa = db.personDoa();

        mAllpersons = mPersonDoa.getAllPersons();
    }

    public LiveData<List<Person>>getAllpersons(){
        return mAllpersons;
    }

    public void insertPerson(Person person){
        new insertAsyncTask(mPersonDoa).execute(person);
    }

    private static class insertAsyncTask extends AsyncTask<Person, Void, Void> {

        private PersonDoa mAsyncTaskDao;

        insertAsyncTask(PersonDoa dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Person... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}

    private static class insertAsyncTask extends AsyncTask<Person, Void, Void> {

        private PersonDoa mAsyncTaskDao;

        insertAsyncTask(PersonDoa dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Person... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}

* */