package com.ardakazanci.samplesocialmediaapp.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.ardakazanci.samplesocialmediaapp.R
import es.dmoral.toasty.Toasty


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


fun <T> Context.intent(it: Class<T>) {

    startActivity(
        Intent(
            this,
            it::class.java
        ).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NO_ANIMATION)
    )

}







