package com.ardakazanci.samplesocialmediaapp.ui.main.ui.messages

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.ApiService
import com.ardakazanci.samplesocialmediaapp.repositories.FollowedListRepository
import com.ardakazanci.samplesocialmediaapp.utils.Constants
import com.securepreferences.SecurePreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MessageFollowedListViewModel(private var app: Application) : AndroidViewModel(app) {

    private val _followedListIsEmpty = MutableLiveData<Boolean>()
    val followedListIsEmpty: LiveData<Boolean>
        get() = _followedListIsEmpty

    var followedListInfo = MutableLiveData<List<DataModel.FollowedListModel>>()

    val mContext: Context = app.applicationContext

    private val LOG_TAG = "MessageFollowedVM"

    // <<< SHARED PREFERENCES INITIALIZE

    val preferences: SharedPreferences =
        SecurePreferences(
            mContext,
            Constants.PREF_USER_TOKEN_VALUE,
            Constants.PREF_USER_TOKEN
        )

    // val userToken: String? = preferences.getString(Constants.PREF_USER_TOKEN_VALUE, null)


    private val _currentUserId = MutableLiveData<String>()
    val currentUserId: LiveData<String>
        get() = _currentUserId

    // <<< REPOSITORY VE COROUTINES ISLEMLERI
    private val followedListRepository: FollowedListRepository =
        FollowedListRepository(ApiService.mainApi)

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)


    init {

        _currentUserId.value = preferences.getString(Constants.PREF_USER_ID_VALUE, null)


        Log.i(LOG_TAG, "OnCreated")
        scope.launch {
            val a = followedListRepository.getFollowedListModel(_currentUserId.value!!)
            try {
                if (a == null) {
                    Log.e(LOG_TAG, "Takip Edilen boş geldi.")
                    _followedListIsEmpty.postValue(true)

                } else {
                    _followedListIsEmpty.postValue(false)
                    a.let {
                        followedListInfo.postValue(a)

                    }
                }


            } catch (e: Exception) {
                Log.e(LOG_TAG, e.printStackTrace().toString())
            }
        }

    }

    fun cancelCoroutines() = coroutineContext.cancel()


    override fun onCleared() {
        super.onCleared()
        Log.i(LOG_TAG, "OnCleared")
    }


}
