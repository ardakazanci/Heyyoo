package com.ardakazanci.samplesocialmediaapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.core.content.ContextCompat
import com.ardakazanci.samplesocialmediaapp.R
import es.dmoral.toasty.Toasty
import java.io.ByteArrayOutputStream


object UtilsFunctions {

    fun getEncoded64ImageStringFromBitmap(bitmap: Bitmap?): String {
        val stream: ByteArrayOutputStream = ByteArrayOutputStream();
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        val byteFormat: ByteArray = stream.toByteArray()
        val imgString: String = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString
    }

    fun uriToBitmapFunction(uri: Uri?, context: Context): String? {
        if (Build.VERSION.SDK_INT < 28) {
            val bitmap = MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                uri
            )
            return getEncoded64ImageStringFromBitmap(bitmap)

        } else {
            val source =
                ImageDecoder.createSource(
                    context.contentResolver,
                    uri!!
                )
            val bitmap = ImageDecoder.decodeBitmap(source)

            return getEncoded64ImageStringFromBitmap(bitmap)
        }
    }

    fun Context.toast(message: CharSequence) {

        Toasty.custom(
            this,
            message,
            ContextCompat.getDrawable(this, R.drawable.ic_info),
            ContextCompat.getColor(this, R.color.color_sign_button),
            ContextCompat.getColor(this, android.R.color.white),
            Toasty.LENGTH_SHORT,
            true,
            true
        ).show();

    }


}


