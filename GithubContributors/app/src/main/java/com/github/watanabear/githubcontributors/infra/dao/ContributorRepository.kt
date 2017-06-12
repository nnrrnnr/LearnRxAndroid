package com.github.watanabear.githubcontributors.infra.dao

import com.github.watanabear.githubcontributors.domain.model.Contributor
import com.github.watanabear.githubcontributors.infra.api.GithubClient
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ryo on 2017/06/12.
 */
@Singleton
class ContributorRepository @Inject constructor(private val client: GithubClient,
                                                private val dao: ContributorDao){

    fun getContributors(): Observable<List<Contributor>> {
        return client.fetchContributors()
                .doOnNext { users -> dao.insert(users) }
                .onErrorReturn { dao.findAll() }
    }
}