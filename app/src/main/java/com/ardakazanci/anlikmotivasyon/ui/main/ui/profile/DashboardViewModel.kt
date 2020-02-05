package com.ardakazanci.anlikmotivasyon.ui.main.ui.profile

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

import android.graphics.Bitmap

import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.MutableLiveData

import com.ardakazanci.anlikmotivasyon.data.network.ApiService
import com.ardakazanci.anlikmotivasyon.repositories.DashboardInfoRepository
import com.ardakazanci.anlikmotivasyon.utils.Constants

import com.ardakazanci.anlikmotivasyon.utils.getDecodeBase64toBitmap

import com.securepreferences.SecurePreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DashboardViewModel(private val app: Application) : AndroidViewModel(app) {


    private var mContext: Context = app.applicationContext
    val preferences: SharedPreferences =
        SecurePreferences(
            mContext,
            Constants.PREF_USER_TOKEN_VALUE,
            Constants.PREF_USER_TOKEN
        )

    val userToken: String? = preferences.getString(Constants.PREF_USER_TOKEN_VALUE, null)
    val userId: String? = preferences.getString(Constants.PREF_USER_ID_VALUE, null)


    private val dashboardInfoRepository: DashboardInfoRepository =
        DashboardInfoRepository(ApiService.mainApi)


    val userFollowerCount = MutableLiveData<Long>()
    val userFollowedCount = MutableLiveData<Long>()
    val userSharedCount = MutableLiveData<Long>()
    val userFullName = MutableLiveData<String>()
    val userProfileImage = MutableLiveData<Bitmap>()


    // <<< COROUTINES BAŞLANGIÇ >>>
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    // <<< COROUTINES SON >>>


    init {

        scope.launch {
            val a = dashboardInfoRepository.getUserInfoResponse(
                userId!!,
                "Bearer " + userToken!!
            )
            try {
                a!!.let {
                    infoDataWith(
                        a.userFullName,
                        a.userFollowerCount,
                        a.userFollowedCount,
                        a.userSharedCount,
                        a.userImageBase64
                    )


                }
            } catch (e: Exception) {

            }


        }

    }


    fun infoDataWith(
        name: String,
        followerCount: Long,
        followedCount: Long,
        sharedCount: Long,
        image: String
    ) {


        userFullName.value = name
        userFollowerCount.value = followerCount
        userFollowedCount.value = followedCount
        userSharedCount.value = sharedCount
        userProfileImage.value = getDecodeBase64toBitmap(image)

    }


    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }

    fun cancelCoroutines() = coroutineContext.cancel()


}