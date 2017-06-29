package co.netguru.android.testcommons;

import android.support.test.espresso.idling.CountingIdlingResource;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;

final class RxIdlingResourceObservableHook {

    private final CountingIdlingResource countingIdlingResource;

    RxIdlingResourceObservableHook(CountingIdlingResource idlingResource) {
        this.countingIdlingResource = idlingResource;
    }

    public Func2<Observable, Observable.OnSubscribe, Observable.OnSubscribe> onStart() {
        return new Func2<Observable, Observable.OnSubscribe, Observable.OnSubscribe>() {
            @Override
            public Observable.OnSubscribe call(Observable observable, Observable.OnSubscribe onSubscribe) {
                countingIdlingResource.increment();
                return onSubscribe;
            }
        };
    }

    public Func1<Subscription, Subscription> onReturn() {
        return new Func1<Subscription, Subscription>() {
            @Override
            public Subscription call(Subscription subscription) {
                countingIdlingResource.decrement();
                return subscription;
            }
        };
    }

    public Func1<Throwable, Throwable> onSubscribeError() {
        return new Func1<Throwable, Throwable>() {
            @Override
            public Throwable call(Throwable throwable) {
                countingIdlingResource.decrement();
                return throwable;
            }
        };
    }
}
