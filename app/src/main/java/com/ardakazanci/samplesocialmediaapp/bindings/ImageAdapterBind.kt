package com.ardakazanci.samplesocialmediaapp.bindings

import android.net.Uri
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.ardakazanci.samplesocialmediaapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.sign_up_fragment.view.*

object ImageAdapterBind {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(imageView: AppCompatImageView, url: Uri?) {
        Glide.with(imageView.context)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.signup_photo_select)
            .into(imageView)
    }


}