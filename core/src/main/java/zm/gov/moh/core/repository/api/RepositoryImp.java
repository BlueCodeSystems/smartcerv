package zm.gov.moh.core.repository.api;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;
import java.util.function.Function;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zm.gov.moh.core.R;
import zm.gov.moh.core.repository.api.rest.RestApi;
import zm.gov.moh.core.repository.api.rest.RestApiImpl;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.dao.domain.PersonAddressDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAttributeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAttributeTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonNameDao;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.Provider;
import zm.gov.moh.core.repository.database.entity.domain.User;
import zm.gov.moh.core.utils.InjectorUtils;

public class RepositoryImp implements Repository{

    private RestApi restapi;
    private Database database;
    private Application application;

    public RepositoryImp(Application application){

        this.database = Database.getDatabase(application);
        this.application = application;
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


    public Database getDatabase() {
        return database;
    }

    public <T>void insertEntityAsync(Consumer<T[]> consumer, T... entities){

        Single.just(entities)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(consumer, throwable -> new Exception(throwable));
    }

    public <T>void insertEntityAsync(Consumer<T> consumer, T entity){

        Single.just(entity)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(consumer, throwable -> new Exception(throwable));
    }

    @Override
    public SharedPreferences getDefaultSharePrefrences() {

        return application.getSharedPreferences(
                application.getResources().getString(R.string.application_shared_prefernce_key),
                Context.MODE_PRIVATE);
    }
}