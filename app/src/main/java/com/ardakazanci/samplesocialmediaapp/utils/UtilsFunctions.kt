package com.ardakazanci.samplesocialmediaapp.utils

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

import es.dmoral.toasty.Toasty
import java.io.ByteArrayOutputStream

import android.annotation.SuppressLint
import com.ardakazanci.samplesocialmediaapp.R
import java.text.SimpleDateFormat
import java.util.*


fun Context.toast(message: String) {

    Toasty.custom(
        this,
        message,
        ContextCompat.getDrawable(this, R.drawable.ic_info),
        ContextCompat.getColor(this, R.color.color_sign_button),
        ContextCompat.getColor(this, android.R.color.white),
        Toasty.LENGTH_SHORT,
        true,
        true
    ).show()

}

fun Fragment.openGalleryForPickingImage(code: Int) {
    Intent(
        Intent.ACTION_PICK,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    ).apply {
        startActivityForResult(Intent.createChooser(this, "Select"), code)
    }
}

fun getEncoded64ImageStringFromBitmap(bitmap: Bitmap?): String {
    val stream: ByteArrayOutputStream = ByteArrayOutputStream();
    bitmap?.compress(Bitmap.CompressFormat.JPEG, 70, stream);
    val byteFormat: ByteArray = stream.toByteArray()
    val imgString: String = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
    return imgString
}


fun getDecodeBase64toBitmap(image: String): Bitmap? {

    val decodeString: ByteArray = Base64.decode(image, Base64.DEFAULT)
    val imageBitmap: Bitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.size)
    return imageBitmap
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

fun bitmapToDrawable(image: Bitmap, resources: Resources): Drawable? {
    val bitmap: Bitmap = image
    val drawable: Drawable = BitmapDrawable(resources, bitmap)
    return drawable
}


@SuppressLint("SimpleDateFormat")
fun unixToDate(timeStamp: Int): String? {
    val time = Date(timeStamp.toLong() * 1000)
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(time)

}








