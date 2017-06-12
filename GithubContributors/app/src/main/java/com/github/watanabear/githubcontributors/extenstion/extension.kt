package com.github.watanabear.githubcontributors.extenstion

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryo on 2017/06/13.
 */


fun <T> Observable<T>.applySchedulers(): Observable<T>
        = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}