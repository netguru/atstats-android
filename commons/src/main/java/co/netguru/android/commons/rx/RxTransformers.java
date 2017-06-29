package co.netguru.android.commons.rx;

import rx.Observable;
import rx.Observable.Transformer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p> Various RxJava {@link Transformer} definitions for code reuse. </p>
 * <p> Use the transformers with {@link Observable#compose(Transformer)} method, for example:</p>
 *
 * <pre> {@code
 * Observable.just(1, 2, 3, 4, 5)
 *      .compose(RxTransformers.androidIO())
 *      .subscribe();
 * } </pre>
 */
public final class RxTransformers {

    private RxTransformers() {}

    /**
     * Transforms observable to execute processing on I/O thread and emit results on Android
     * main (UI) thread.
     *
     * @param <T> Observable parameter
     * @return Observable subscribed on {@link Schedulers#io()} and observed on
     * {@link AndroidSchedulers#mainThread()}.
     */
    public static <T> Transformer<T, T> androidIO() {
        return observable -> observable.subscribeOn(Schedulers.io())
                                     .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Converts an {@link Observable} < {@link Iterable}{@literal <T>>} to
     * {@link Observable}{@literal <T>}
     *
     * @param <T> Observable parameter
     * @return new Observable from the contents of original Observable's {@link Iterable} value
     */
    public static <T> Transformer<Iterable<T>, T> fromListObservable() {
        return observable -> observable.flatMap(Observable::from);
    }

}
