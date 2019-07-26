package zm.gov.moh.core.utils;

import java.util.AbstractMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ConcurrencyUtils {

    public static <T>Disposable consumeAsync(Consumer<T> consumer, Consumer<Throwable> onError, T items){

        return Single.just(items)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(consumer, onError);
    }

    public static <T1, T2>Disposable consumeAsync(BiConsumer<T1,T2> consumer, Consumer<Throwable> onError, T1 param1, T2 param2, final int timeout){

        AbstractMap.SimpleImmutableEntry<T1,T2>  params = new AbstractMap.SimpleImmutableEntry<>(param1,param2);
        return Single.just(params)
                .timeout(timeout, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(p -> consumer.accept(p.getKey(),p.getValue()), onError);
    }

    public static  <T,R>Disposable asyncFunction(Function<T,R> function, Consumer<R> consumer, T items, Consumer<Throwable> onError){

        return Single.just(items)
                .subscribeOn(Schedulers.io())
                .map(function)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, onError);
    }

    public static Disposable asyncRunnable(Runnable runnable, Consumer<Throwable> onError){

        return Single.just("")
                .subscribeOn(Schedulers.io())
                .map(a->{ runnable.run(); return "";})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(a->{ }, onError);
    }

    public static  <T>Disposable consumeAsync(Consumer<T> consumer, Consumer<Throwable> onError, Maybe<T> observable, final int timeout) {

        return observable.timeout(timeout, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(consumer, onError);

    }

    public static <T>Disposable consumeAsync(Consumer<T[]> consumer, Consumer<Throwable> onError, Action onComplete, Maybe<T[]> observable, final int timeout) {

        return observable.timeout(timeout, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnComplete(onComplete)
                .subscribe(consumer, onError);

    }

    public static  <T>Disposable consume(Consumer<T> consumer, Consumer<Throwable> failure, Maybe<T> observable,  final int timeout) {

        return observable.timeout(timeout, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, failure);
    }

    public static  <T>Disposable consumeBlocking(Consumer<T> consumer, Consumer<Throwable> failure, Maybe<T> observable, final int timeout) {

        return observable.timeout(timeout, TimeUnit.MILLISECONDS)
                .subscribe(consumer, failure);
    }
}
