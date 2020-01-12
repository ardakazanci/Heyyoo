package com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.pager.shared

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.ApiService
import com.ardakazanci.samplesocialmediaapp.repositories.ProfileSharedListRepository
import com.ardakazanci.samplesocialmediaapp.utils.Constants
import com.securepreferences.SecurePreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ProfileSharedViewModel(private var app: Application) : AndroidViewModel(app) {


    private val mContext = app.applicationContext
    private val LOG_TAG = "ProfileSharedViewModel"

    // SharedPrefs
    private val preferences: SharedPreferences =
        SecurePreferences(mContext, Constants.PREF_USER_TOKEN_VALUE, Constants.PREF_USER_TOKEN)
    val userToken: String? = preferences.getString(Constants.PREF_USER_TOKEN_VALUE, null)
    val userId: String? = preferences.getString(Constants.PREF_USER_ID_VALUE, null)

    private val _userSharedContents = MutableLiveData<List<DataModel.ContentDataModel>>()
    val userSharedContents: LiveData<List<DataModel.ContentDataModel>>
        get() = _userSharedContents

    private val userSharedContent: ProfileSharedListRepository =
        ProfileSharedListRepository(ApiService.mainApi)


    // Coroutines
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)


    init {

        userSharedGetContentInfo()
    }


    private fun userSharedGetContentInfo() {

        scope.launch {
            userId!!.let {


                val a = userSharedContent.getUserSharedContent(userId)

                try {

                    a?.let {
                        it.forEach {
                            Log.e("VMDeğer", it.contentText)
                        }
                        _userSharedContents.postValue(it)
                    }


                } catch (e: Exception) {
                    Log.e(LOG_TAG, e.printStackTrace().toString())
                }
            }

        }


    }


    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }


    fun onContextCancel() = coroutineContext.cancel()

}
