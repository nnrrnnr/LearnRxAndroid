package com.github.watanabear.githubcontributors.presentation

import android.app.Application
import com.github.watanabear.githubcontributors.di.ApplicationComponent
import com.github.watanabear.githubcontributors.di.ApplicationModule
import com.github.watanabear.githubcontributors.di.DaggerApplicationComponent
import timber.log.Timber

/**
 * Created by ryo on 2017/06/12.
 */
class App : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}