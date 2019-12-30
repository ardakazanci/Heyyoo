package com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.otherprofile

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.ApiService
import com.ardakazanci.samplesocialmediaapp.repositories.DashboardInfoRepository
import com.ardakazanci.samplesocialmediaapp.repositories.FollowedListRepository
import com.ardakazanci.samplesocialmediaapp.repositories.UnFollowRepository
import com.ardakazanci.samplesocialmediaapp.utils.Constants
import com.ardakazanci.samplesocialmediaapp.utils.getDecodeBase64toBitmap
import com.securepreferences.SecurePreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class OtherProfileViewModel(private val app: Application) : AndroidViewModel(app) {

    private val LOG_TAG = "OtherProfileVM"
    private val mContext: Context = app.applicationContext


    val preferences: SharedPreferences =
        SecurePreferences(
            mContext,
            Constants.PREF_USER_TOKEN_VALUE,
            Constants.PREF_USER_TOKEN
        )

    val userToken: String? = preferences.getString(Constants.PREF_USER_TOKEN_VALUE, null)
    val userId: String? = preferences.getString(Constants.PREF_USER_ID_VALUE, null)
    val _otherUserId = MutableLiveData<String>()


    private val _otherUserIsFollow = MutableLiveData<Boolean>()
    val otherUserIsFollow: LiveData<Boolean>
        get() = _otherUserIsFollow


    private val dashboardInfoRepository: DashboardInfoRepository =
        DashboardInfoRepository(ApiService.mainApi)

    private val followedListRepository: FollowedListRepository =
        FollowedListRepository(ApiService.mainApi)

    private val unFollowRepository: UnFollowRepository = UnFollowRepository(ApiService.mainApi)

    // <<< DATA-BINDING ISLEMLERİ
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
        Log.i(LOG_TAG, "OnCreated ${_otherUserId.value}")
        scope.launch {
            val a = dashboardInfoRepository.getUserInfoResponse(
                _otherUserId.value!!,
                "Bearer " + userToken!!
            )

            val followedList = followedListRepository.getFollowedListModel(userId!!)
            try {


                a!!.let {

                    followedList!!.let {


                        infoDataWith(
                            a.userFullName,
                            a.userFollowerCount,
                            a.userFollowedCount,
                            a.userSharedCount,
                            a.userImageBase64
                        )
                        followedList.forEach {

                            if (it._id == _otherUserId.value) {
                                Log.e(LOG_TAG, "Bu profilde ki kullanıcı takip ediliyormuş")
                                _otherUserIsFollow.postValue(true)
                            }

                        }

                    }


                }


            } catch (e: Exception) {

            }

        }

    }

    fun unFollowUser() {
        scope.launch {


            try {

                Log.e("UserID->", userId!!)
                Log.e("OtherUserId->", _otherUserId.value!!)
                val unFollowProcess = unFollowRepository.getUnFollowResponse(
                    userId!!, _otherUserId.value!!
                )

                unFollowProcess!!.let {
                    _otherUserIsFollow.postValue(false)

                }


            } catch (e: Exception) {
                Log.e(LOG_TAG, "UnFollow işleminde problem yaşandı")
            }

        }

        userFollowerCount.value = userFollowerCount.value?.minus(1)
    }

    fun followUser() {

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
        Log.i(LOG_TAG, "OnCleared")
    }
}



