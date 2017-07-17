package com.github.watanabear.learn_rxjava2_android.presentation.operators;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.watanabear.learn_rxjava2_android.R;
import com.github.watanabear.learn_rxjava2_android.databinding.ActivityOperatorsBinding;
import com.github.watanabear.learn_rxjava2_android.util.AppConstant;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SimpleActivity extends AppCompatActivity {

    private static final String TAG = "SimpleActivity";

    private ActivityOperatorsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_operators);
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSomeWork();
            }
        });
    }

    private void doSomeWork() {
        getObservable()
                //Run on a background thread
                .subscribeOn(Schedulers.io())
                //Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }

    private Observable<String> getObservable() {
        return Observable.just("one", "two");
    }

    private Observer<String> getObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: " + d.isDisposed());
            }

            @Override
            public void onNext(String s) {
                binding.textView.append(" onNext : value : " + s);
                binding.textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "onNext: value: " + s);
            }

            @Override
            public void onError(Throwable e) {
                binding.textView.append(" onError: " + e.getMessage());
                binding.textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                binding.textView.append(" onComplete");
                binding.textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "onComplete: ");
            }
        };
    }
}
