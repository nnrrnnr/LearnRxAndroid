package com.github.watanabear.githubcontributors.presentation.util

import android.databinding.BindingAdapter
import android.widget.ArrayAdapter
import android.widget.GridView
import com.github.watanabear.githubcontributors.domain.model.Contributor
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

/**
 * Created by ryo on 2017/06/15.
 */

@BindingAdapter("bing:adapter")
fun GridView.bindAdapter(adapter: ArrayAdapter<Contributor>) {
    this.adapter = adapter
}
@BindingAdapter("bind:imageUrl")
fun RoundedImageView.setImageUrl(url: String) {
    Picasso.with(context).load(url).into(this)
}