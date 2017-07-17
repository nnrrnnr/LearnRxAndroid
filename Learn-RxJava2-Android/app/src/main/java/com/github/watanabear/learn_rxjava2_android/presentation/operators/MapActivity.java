package com.github.watanabear.learn_rxjava2_android.presentation.operators;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.watanabear.learn_rxjava2_android.R;
import com.github.watanabear.learn_rxjava2_android.databinding.ActivityOperatorsBinding;
import com.github.watanabear.learn_rxjava2_android.model.ApiUser;
import com.github.watanabear.learn_rxjava2_android.model.User;
import com.github.watanabear.learn_rxjava2_android.util.AppConstant;
import com.github.watanabear.learn_rxjava2_android.util.Utils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MapActivity extends AppCompatActivity {

    private static final String TAG = MapActivity.class.getSimpleName();

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
                .map(new Function<List<ApiUser>, List<User>>() {
                    @Override
                    public List<User> apply(@NonNull List<ApiUser> apiUsers) throws Exception {
                        return Utils.convertApiUserListToUserList(apiUsers);
                    }
                })
                .subscribe(getObserver());
    }

    private Observable<List<ApiUser>> getObservable() {
        return Observable.create(new ObservableOnSubscribe<List<ApiUser>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ApiUser>> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(Utils.getApiUserList());
                    e.onComplete();
                }
            }
        });
    }

    private Observer<List<User>> getObserver() {
        return new Observer<List<User>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(List<User> users) {
                binding.textView.append(" onNext");
                binding.textView.append(AppConstant.LINE_SEPARATOR);
                for (User user : users) {
                    binding.textView.append(" firstname : " + user.firstName);
                    binding.textView.append(AppConstant.LINE_SEPARATOR);
                }
                Log.d(TAG, " onNext : " + users.size());
            }

            @Override
            public void onError(Throwable e) {
                binding.textView.append(" onError : " + e.getMessage());
                binding.textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                binding.textView.append(" onComplete");
                binding.textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " onComplete");
            }
        };
    }
}
