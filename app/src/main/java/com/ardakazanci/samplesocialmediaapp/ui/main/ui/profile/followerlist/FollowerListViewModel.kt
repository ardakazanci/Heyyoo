package com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.followerlist

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.ApiService
import com.ardakazanci.samplesocialmediaapp.repositories.FollowerListRepository
import com.ardakazanci.samplesocialmediaapp.utils.Constants
import com.securepreferences.SecurePreferences
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class FollowerListViewModel(private var app: Application) : AndroidViewModel(app) {


    private val _followerListIsEmpty = MutableLiveData<Boolean>()
    val followerListIsEmpty: LiveData<Boolean>
        get() = _followerListIsEmpty


    var followerListInfo = MutableLiveData<List<DataModel.FollowerListModel>>()



    private val mContext: Context = app.applicationContext
    private val LOG_TAG: String = "FollowerVM"

    // <<< SHAREDPREF GEREKEN DEĞERLER >>>
    val preferences: SharedPreferences =
        SecurePreferences(
            mContext,
            Constants.PREF_USER_TOKEN_VALUE,
            Constants.PREF_USER_TOKEN
        )

    val userToken: String? = preferences.getString(Constants.PREF_USER_TOKEN_VALUE, null)
    val userId: String? = preferences.getString(Constants.PREF_USER_ID_VALUE, null)


    // <<< REPOSITORY VE COROUTINES KURULUMLARI
    private val followerListInfoRepository: FollowerListRepository =
        FollowerListRepository(ApiService.mainApi)


    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)


    init {
        Log.i(LOG_TAG, "OnCreated")
        scope.launch {
            val a = followerListInfoRepository.getFollowerListInfo(userId!!)
            try {

                if (a == null) {

                    _followerListIsEmpty.postValue(true)
                } else {

                    a.let {

                        followerListInfo.postValue(a)
                        _followerListIsEmpty.postValue(false)

                    }

                }


            } catch (e: Exception) {
                Log.e(LOG_TAG, e.printStackTrace().toString())
            }
        }


    }

    override fun onCleared() {
        super.onCleared()
        Log.i(LOG_TAG, "OnCleared")
    }

    fun cancelCoroutines() = coroutineContext.cancel()


}
