package com.github.watanabear.githubcontributors.infra.api

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ryo on 2017/06/12.
 */
@Singleton
class GithubClient @Inject constructor(private val service: GithubService) {

    fun fetchContributors() = service.fetchContributors("DroidKaigi",
            "conference-app-2017", 100)
}