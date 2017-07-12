package com.github.watanabear.githubcontributors.presentation

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.watanabear.githubcontributors.R
import com.github.watanabear.githubcontributors.databinding.ActivityMainBinding
import com.github.watanabear.githubcontributors.di.MainModule
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)}
    @Inject lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).component.plus(MainModule()).inject(this)

        binding.viewModel = viewModel
        viewModel.getContributors()
    }
}
