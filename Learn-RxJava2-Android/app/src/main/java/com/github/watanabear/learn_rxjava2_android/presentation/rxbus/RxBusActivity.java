package com.github.watanabear.learn_rxjava2_android.presentation.rxbus;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.watanabear.learn_rxjava2_android.MyApplication;
import com.github.watanabear.learn_rxjava2_android.R;
import com.github.watanabear.learn_rxjava2_android.databinding.ActivityRxbusBinding;
import com.github.watanabear.learn_rxjava2_android.model.Events;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxBusActivity extends AppCompatActivity {

    private static final String TAG = "RxBusActivity";
    private ActivityRxbusBinding binding;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rxbus);

        disposables.add(((MyApplication) getApplication())
                .bus()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        if (o instanceof Events.AutoEvent) {
                            binding.textView.setText("Auto Event Received");
                        } else if (o instanceof Events.TapEvent) {
                            binding.textView.setText("Tap Event Received");
                        }
                    }
                })
        );

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyApplication) getApplication())
                        .bus()
                        .send(new Events.TapEvent());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();//Activityがdestroyedの後にイベントを送らない
    }
}
