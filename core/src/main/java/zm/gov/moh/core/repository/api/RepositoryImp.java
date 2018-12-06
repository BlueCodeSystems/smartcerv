package zm.gov.moh.core.repository.api;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.TimeUnit;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zm.gov.moh.core.R;
import zm.gov.moh.core.repository.api.rest.RestApi;
import zm.gov.moh.core.repository.api.rest.RestApiAdapter;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.utils.InjectorUtils;

public class RepositoryImp implements Repository{

    private RestApi restapi;
    private Database database;
    private RestApiAdapter restApiAdapter;
    private Application application;


    public RepositoryImp(Application application){

        this.application = application;
        this.database = Database.getDatabase(application);
        this.restApiAdapter = InjectorUtils.provideRestAPIAdapter();
    }


    public Database getDatabase() {
        return database;
    }

    @Override
    public RestApiAdapter getRestApiAdapter() {
        return restApiAdapter;
    }

    @Deprecated
    public <T>void consumeAsync(Consumer<T[]> consumer, T... items){

        Single.just(items)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(consumer, throwable -> new Exception(throwable));
    }

    public <T>void consumeAsync(Consumer<T> consumer, Consumer<Throwable> onError, T items){

        Single.just(items)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(consumer, onError);
    }

    public <T>void consumeAsync(Consumer<T> consumer, Consumer<Throwable> onError, Maybe<T> observable, final int timeout) {

        observable.timeout(timeout, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(consumer, onError);

    }

    public <T>void consumeAsync(Consumer<T[]> consumer, Consumer<Throwable> onError, Action onComplete, Maybe<T[]> observable, final int timeout) {

        observable.timeout(timeout, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnComplete(onComplete)
                .subscribe(consumer, onError);

    }

    public <T>void consume(Consumer<T> consumer, Consumer<Throwable> failure, Maybe<T> observable,  final int timeout) {

        observable.timeout(timeout, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, failure);

    }

    @Override
    public SharedPreferences getDefaultSharePrefrences() {

        return application.getSharedPreferences(
                application.getResources().getString(R.string.application_shared_prefernce_key),
                Context.MODE_PRIVATE);
    }
}