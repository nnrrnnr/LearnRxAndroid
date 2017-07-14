package com.github.watanabear.learn_rxjava2_android;

import android.app.Application;

import com.github.watanabear.learn_rxjava2_android.model.Events;
import com.github.watanabear.learn_rxjava2_android.presentation.rxbus.RxBus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by ryo on 2017/07/14.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    private RxBus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        bus = new RxBus();
    }

    public RxBus bus() {
        return bus;
    }

    public void sendAutoEvent() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        bus.send(new Events.AutoEvent());
                    }
                });
    }



}
