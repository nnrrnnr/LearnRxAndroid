package com.github.watanabear.githubcontributors.presentation

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.github.watanabear.githubcontributors.BR
import com.github.watanabear.githubcontributors.R
import com.github.watanabear.githubcontributors.databinding.ItemContributorBinding
import com.github.watanabear.githubcontributors.domain.model.Contributor
import com.github.watanabear.githubcontributors.extenstion.applySchedulers
import com.github.watanabear.githubcontributors.infra.repository.ContributorRepository
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by ryo on 2017/06/12.
 */
class MainViewModel @Inject constructor(val context: Context, private val repository: ContributorRepository):BaseObservable(){

    @Bindable
    var loading = true
    set(value) {
        field = value
        notifyPropertyChanged(BR.loading)
    }

    @Bindable
    var adapter = ContributorsAdapter(context, emptyList())
    set(value) {
        field = value
        notifyPropertyChanged(BR.adapter)
    }

    fun getContributors() {
        repository.getContributors()
                .applySchedulers()
                .subscribe(
                        {
                            adapter = ContributorsAdapter(context, it)
                            Timber.d("" + it.size + " : ok")
                            loading = false
                        },
                        {
                            Timber.e(it, "getcontributers")
                            loading = false
                        }
                )
    }

    class ContributorsAdapter(context: Context, contributors: List<Contributor>) :
            ArrayAdapter<Contributor>(context, 0, contributors) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val binding: ItemContributorBinding
            if (convertView == null) {
                binding = ItemContributorBinding.bind(View.inflate(context, R.layout.item_contributor, null))
                binding.root.tag = binding
            } else {
                binding = convertView.tag as ItemContributorBinding
            }
            binding.contributor = getItem(position)
            return binding.root
        }

    }

}