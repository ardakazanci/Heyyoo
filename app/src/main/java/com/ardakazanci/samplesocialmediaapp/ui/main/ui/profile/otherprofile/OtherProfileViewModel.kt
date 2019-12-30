package com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.otherprofile

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class OtherProfileViewModel(private val app: Application) : AndroidViewModel(app) {

    private val LOG_TAG = "OtherProfileVM"
    private val mContext: Context = app.applicationContext

    init {
        Log.i(LOG_TAG, "OnCreated")

    }

    // Gelen USERID ve BOOLEAN Değelerinin alınması gerek.


    override fun onCleared() {
        super.onCleared()
        Log.i(LOG_TAG, "OnCleared")
    }


}
