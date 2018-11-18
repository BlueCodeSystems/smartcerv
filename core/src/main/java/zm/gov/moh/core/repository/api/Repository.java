package zm.gov.moh.core.repository.api;

import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;

import java.util.List;

import io.reactivex.functions.Consumer;
import zm.gov.moh.core.repository.api.rest.RestApi;
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


public interface Repository {

    RestApi getRestApi(String token);

    public <T>void insertEntityAsync(Consumer<T[]> consumer, T... entities);

    public <T>void insertEntityAsync(Consumer<T> consumer, T entity);

    Database getDatabase();

    SharedPreferences getDefaultSharePrefrences();
}
