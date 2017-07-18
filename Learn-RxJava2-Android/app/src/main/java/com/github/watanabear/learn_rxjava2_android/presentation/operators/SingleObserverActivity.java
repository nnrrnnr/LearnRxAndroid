package com.github.watanabear.learn_rxjava2_android.presentation.operators;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.watanabear.learn_rxjava2_android.R;
import com.github.watanabear.learn_rxjava2_android.databinding.ActivityOperatorsBinding;
import com.github.watanabear.learn_rxjava2_android.util.AppConstant;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class SingleObserverActivity extends AppCompatActivity {

    private static final String TAG = SingleObserverActivity.class.getSimpleName();

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

    /**
     * example using SingleObserver
     */
    private void doSomeWork() {
        Single.just("Amit").subscribe(getSingleObserver());
    }

    private SingleObserver<String> getSingleObserver() {
        return new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: " + d.isDisposed());
            }

            @Override
            public void onSuccess(String s) {
                binding.textView.append("onSuccess: value: " + s);
                binding.textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "onSuccess: value:" + s);
            }

            @Override
            public void onError(Throwable e) {
                binding.textView.append("onError: " + e.getMessage());
                binding.textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "onError: " + e.getMessage());
            }
        };
    }

}
