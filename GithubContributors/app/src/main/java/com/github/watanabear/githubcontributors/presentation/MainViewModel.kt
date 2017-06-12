package com.github.watanabear.githubcontributors.presentation

import com.github.watanabear.githubcontributors.extenstion.addTo
import com.github.watanabear.githubcontributors.extenstion.applySchedulers
import com.github.watanabear.githubcontributors.infra.dao.ContributorRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by ryo on 2017/06/12.
 */
class MainViewModel @Inject constructor(private val repository: ContributorRepository) {

    private val disposables = CompositeDisposable()

    fun getContributors() {
        repository.getContributors()
                .applySchedulers()
                .subscribe(
                        { Timber.d("" + it.size + " : ok")},
                        { Timber.e(it, "getcontributers") }
                )
                .addTo(disposables)
    }

    fun dispose() {
        disposables.dispose()
    }

}