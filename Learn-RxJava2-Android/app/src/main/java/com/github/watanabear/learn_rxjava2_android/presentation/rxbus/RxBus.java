package com.github.watanabear.learn_rxjava2_android.presentation.rxbus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by ryo on 2017/07/14.
 */

public class RxBus {

    public RxBus() {}

    private PublishSubject<Object> bus = PublishSubject.create();

    public void send(Object o) {
        bus.onNext(o);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}
