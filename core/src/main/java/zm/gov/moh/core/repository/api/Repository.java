package zm.gov.moh.core.repository.api;

import android.content.SharedPreferences;

import io.reactivex.Maybe;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import zm.gov.moh.core.repository.api.rest.RestApi;
import zm.gov.moh.core.repository.database.Database;


public interface Repository {

    @Deprecated
    <T>void consumeAsync(Consumer<T[]> consumer, T... entities);

    <T>void consumeAsync(Consumer<T> consumer, Consumer<Throwable> onError, T items);

    <T1, T2>void consumeAsync(BiConsumer<T1,T2> consumer, Consumer<Throwable> onError, T1 param1, T2 param2);

    <T>void consumeAsync(Consumer<T> consumer, Consumer<Throwable> onError, Maybe<T> observable, final int timeout);

    <T>void consume(Consumer<T> consumer, Consumer<Throwable> onError, Maybe<T> observable, final int timeout);

    <T>void consumeAsync(Consumer<T[]> consumer, Consumer<Throwable> onError, Action onComplete, Maybe<T[]> observable, final int timeout);

    <T,R>void asyncFunction(Function<T, R> function, Consumer<R> consumer, T items, Consumer<Throwable> onError);

    void asyncRunnable(Runnable runnable, Consumer<Throwable> onError);

    Database getDatabase();

    SharedPreferences getDefaultSharePrefrences();

    RestApi getRestApi();
}
