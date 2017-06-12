package com.github.watanabear.learnrxandroid.infra

import android.content.Context
import com.github.watanabear.learnrxandroid.presentation.App
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

/**
 * Created by ryo on 2017/06/12.
 */
@Module
class ApplicationModule(private val applicationContext: App) {

    private val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

    @Provides
    fun provideContext(): Context {
        return applicationContext
    }
}