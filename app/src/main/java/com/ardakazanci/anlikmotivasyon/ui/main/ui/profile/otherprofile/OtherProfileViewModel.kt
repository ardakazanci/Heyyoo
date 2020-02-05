package com.ardakazanci.anlikmotivasyon.ui.main.ui.profile.otherprofile

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ardakazanci.anlikmotivasyon.data.network.ApiService
import com.ardakazanci.anlikmotivasyon.repositories.DashboardInfoRepository
import com.ardakazanci.anlikmotivasyon.repositories.FollowRepository
import com.ardakazanci.anlikmotivasyon.repositories.FollowedListRepository
import com.ardakazanci.anlikmotivasyon.repositories.UnFollowRepository
import com.ardakazanci.anlikmotivasyon.utils.Constants
import com.ardakazanci.anlikmotivasyon.utils.getDecodeBase64toBitmap
import com.securepreferences.SecurePreferences
import kotlinx.coroutines.*
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
    private val followRepository: FollowRepository = FollowRepository(ApiService.mainApi)

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
        Log.i(LOG_TAG, "OnCreated $userId")
        scope.launch {


            val a = dashboardInfoRepository.getUserInfoResponse(
                _otherUserId.value!!,
                "Bearer " + userToken!!
            )





            try {


                a!!.let {

                    val userFullName = a.userFullName
                    val userSharedCount = a.userSharedCount
                    val userFollowedCount = a.userFollowedCount
                    val userFollowerCount = a.userFollowerCount
                    val userEmail = a.userEmail
                    val userImageBase64 = a.userImageBase64

                    if (userSharedCount == 0L && userFollowedCount == 0L && userFollowerCount == 0L) {
                        infoDataWith(
                            a.userFullName,
                            a.userFollowerCount,
                            a.userFollowedCount,
                            a.userSharedCount,
                            a.userImageBase64
                        )
                        _otherUserIsFollow.postValue(false)
                    } else {
                        val followedList = followedListRepository.getFollowedListModel(userId!!)
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




                }


            } catch (e: Exception) {

            }

        }

    }

    fun unFollowUser() {
        scope.launch {


            try {

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

        // Takipten Çıkma işlemi başarıyla gerçekleştiğinde ilgili kullanıcının takipçi sayısı 1 azaltılıyor.
        userFollowerCount.value = userFollowerCount.value?.minus(1)
    }

    fun followUser() {
        scope.launch {
            try {
                val followProcess = followRepository.getFollowResponse(
                    _otherUserId.value!!, userId!!
                )
                followProcess!!.let {
                    _otherUserIsFollow.postValue(true)
                }

            } catch (e: Exception) {
                Log.e(LOG_TAG, "Follow işleminde problem yaşandı")
            }
        }
        userFollowerCount.value = userFollowerCount.value?.plus(1)
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

    fun onClearedCoroutines() = coroutineContext.cancel()
}



