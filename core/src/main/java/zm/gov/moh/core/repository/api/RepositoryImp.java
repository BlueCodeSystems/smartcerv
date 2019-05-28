package zm.gov.moh.core.repository.api;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.AbstractMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import zm.gov.moh.core.R;
import zm.gov.moh.core.repository.api.rest.RestApi;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.utils.InjectorUtils;

public class RepositoryImp implements Repository{

    private Database database;
    private RestApi restApi;
    private Application application;


    public RepositoryImp(Application application){

        this.application = application;
        this.database = Database.getDatabase(application);
        this.restApi = InjectorUtils.provideRestAPIAdapter();
    }


    public Database getDatabase() {
        return database;
    }

    @Override
    public RestApi getRestApi() {
        return restApi;
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

    public <T1, T2>void consumeAsync(BiConsumer<T1,T2> consumer, Consumer<Throwable> onError, T1 param1, T2 param2){

        AbstractMap.SimpleImmutableEntry<T1,T2>  params = new AbstractMap.SimpleImmutableEntry<>(param1,param2);
        Single.just(params)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(p -> consumer.accept(p.getKey(),p.getValue()), onError);
    }

    public <T,R>void asyncFunction(Function<T,R> function,Consumer<R> consumer,T items,Consumer<Throwable> onError){

        Single.just(items)
                .subscribeOn(Schedulers.io())
                .map(function)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, onError);
    }

    public void asyncRunnable(Runnable runnable, Consumer<Throwable> onError){

        Single.just("")
                .subscribeOn(Schedulers.io())
                .map(a->{ runnable.run(); return null;})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(a->{ }, onError);
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