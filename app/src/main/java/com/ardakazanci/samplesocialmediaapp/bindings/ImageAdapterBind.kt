package com.ardakazanci.samplesocialmediaapp.bindings

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.ardakazanci.samplesocialmediaapp.R
import com.ardakazanci.samplesocialmediaapp.utils.getDecodeBase64toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
            .placeholder(R.drawable.ic_app_global)
            .into(imageView)
    }


    @BindingAdapter("imageUrlNoCircle")
    @JvmStatic
    fun loadImageNoCircle(imageView: CircleImageView, imageBase64: String?) {
        try {

            imageBase64!!.let {

                val options = RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.ic_error_outline_white_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)

                val base64toBitmap = getDecodeBase64toBitmap(imageBase64)
                Glide.with(imageView.context)
                    .load(base64toBitmap)
                    .apply(options)
                    .into(imageView)

            }

        } catch (e: Exception) {

            Log.e("ImageAdapter", e.printStackTrace().toString())

        }


    }


    @BindingAdapter("imageBitmap")
    @JvmStatic
    fun setImageViewResource(imageView: CircleImageView, resource: Bitmap?) {

        try {

            val options = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.ic_error_outline_white_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)

            Glide.with(imageView.context)
                .load(resource)
                .apply(options)
                .into(imageView)
        } catch (e: Exception) {
            Log.e("Bitmap", "Image yüklerken gecikme oldu")
        }

    }

}


