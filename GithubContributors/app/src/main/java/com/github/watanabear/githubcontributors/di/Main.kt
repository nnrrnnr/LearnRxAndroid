package com.github.watanabear.githubcontributors.di

import com.github.watanabear.githubcontributors.presentation.MainActivity
import dagger.Module
import dagger.Subcomponent

/**
 * Created by ryo on 2017/06/12.
 */
@Module
class MainModule

@Subcomponent(modules = arrayOf(MainModule::class))
interface  MainComponent {
    fun inject(a: MainActivity)
}