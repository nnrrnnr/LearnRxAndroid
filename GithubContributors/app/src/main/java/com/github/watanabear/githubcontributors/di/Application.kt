package com.github.watanabear.githubcontributors.di

import android.content.Context
import com.github.gfx.android.orma.AccessThreadConstraint
import com.github.watanabear.githubcontributors.domain.model.OrmaDatabase
import com.github.watanabear.githubcontributors.infra.api.GithubService
import com.github.watanabear.githubcontributors.presentation.App
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

/**
 * Created by ryo on 2017/06/12.
 */
@Module
class ApplicationModule(private val application: App) {

    private val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    @Provides
    fun provideContext(): Context {
        return application
    }

    @Provides
    fun provideOkHttp(): OkHttpClient {
        val logger = HttpLoggingInterceptor { log -> Timber.tag("okhttp").v(log) }
        logger.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    fun provideGithubService(retrofit: Retrofit): GithubService {
        return retrofit.create(GithubService::class.java)
    }

    @Provides
    fun provideOrma(context: Context): OrmaHolder {
        val orma = OrmaDatabase.builder(context)
                .writeOnMainThread(AccessThreadConstraint.FATAL)
                .readOnMainThread(AccessThreadConstraint.FATAL)
                .trace(true)
                .build()
        return OrmaHolder(orma)
    }


    class OrmaHolder(val orma: OrmaDatabase)
}

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun plus(m: MainModule): MainComponent
}