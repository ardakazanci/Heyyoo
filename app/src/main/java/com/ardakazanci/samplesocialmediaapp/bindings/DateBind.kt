package com.ardakazanci.samplesocialmediaapp.bindings

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

object DateBind {

    @SuppressLint("SimpleDateFormat")
    @BindingAdapter("datevisible")
    @JvmStatic
    fun dateVisible(view: TextView, unixtDate: Long) {
        val time = Date(unixtDate.toLong() * 1000)
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        Log.e("Date", sdf.toString())
        view.text = sdf.format(time)
    }


}