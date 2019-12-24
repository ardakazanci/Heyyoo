package com.ardakazanci.samplesocialmediaapp.utils

import android.content.Context
import com.qingmei2.rximagepicker.entity.Result
import com.qingmei2.rximagepicker.entity.sources.Gallery
import io.reactivex.Observable

interface IMyImagePicker {

    @Gallery
    fun openGallery(context: Context): Observable<Result>

}