package zm.gov.moh.core.repository.api;

import android.content.SharedPreferences;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import zm.gov.moh.core.repository.api.rest.RestApi;
import zm.gov.moh.core.repository.api.rest.RestApiAdapter;
import zm.gov.moh.core.repository.database.Database;



public interface Repository {

    @Deprecated
    <T>void consumeAsync(Consumer<T[]> consumer, T... entities);

    <T>void consumeAsync(Consumer<T> consumer, Consumer<Throwable> onError, T items);

    <T>void consumeAsync(Consumer<T> consumer, Consumer<Throwable> onError, Maybe<T> observable, final int timeout);

    <T>void consume(Consumer<T> consumer, Consumer<Throwable> onError, Maybe<T> observable, final int timeout);

    public <T>void consumeAsync(Consumer<T[]> consumer, Consumer<Throwable> onError, Action onComplete, Maybe<T[]> observable, final int timeout);

    public <T,R>void asyncFunction(Function<T, R> function, Consumer<R> consumer, T items, Consumer<Throwable> onError);

    Database getDatabase();

    SharedPreferences getDefaultSharePrefrences();

    RestApiAdapter getRestApiAdapter();
}
