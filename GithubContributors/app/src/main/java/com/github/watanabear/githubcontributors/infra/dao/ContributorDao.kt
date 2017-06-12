package com.github.watanabear.githubcontributors.infra.dao

import com.github.gfx.android.orma.annotation.OnConflict
import com.github.watanabear.githubcontributors.di.ApplicationModule
import com.github.watanabear.githubcontributors.domain.model.Contributor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ryo on 2017/06/12.
 */
@Singleton
class ContributorDao @Inject constructor(ormaHolder: ApplicationModule.OrmaHolder) {

    private val orma = ormaHolder.orma

    fun insert(contributors: List<Contributor>) {
        orma.transactionSync {
            orma.prepareInsertIntoContributor(OnConflict.REPLACE).executeAll(contributors)
        }
    }

    fun findAll(): List<Contributor> = orma.selectFromContributor().toList()
}