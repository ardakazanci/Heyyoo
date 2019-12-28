package com.ardakazanci.samplesocialmediaapp.bindings

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.ardakazanci.samplesocialmediaapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.sign_up_fragment.view.*

import de.hdodenhof.circleimageview.CircleImageView


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


    @BindingAdapter("imageBitmap")
    @JvmStatic
    fun setImageViewResource(imageView: CircleImageView, resource: Bitmap?) {

        try {

            Glide.with(imageView.context)
                .load(resource)
                .placeholder(R.drawable.signup_photo_select)
                .into(imageView)
        } catch (e: Exception) {
            Log.e("Bitmap", "Image yüklerken gecikme oldu")
        }

    }

}


