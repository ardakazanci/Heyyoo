package com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardakazanci.samplesocialmediaapp.data.network.ApiService
import com.ardakazanci.samplesocialmediaapp.repositories.DashboardInfoRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DashboardViewModel : ViewModel() {

    private val dashboardInfoRepository: DashboardInfoRepository =
        DashboardInfoRepository(ApiService.mainApi)


    val userFollowerCount = MutableLiveData<Long>()
    val userFollowedCount = MutableLiveData<Long>()
    val userSharedCount = MutableLiveData<Long>()
    val userFullName = MutableLiveData<String>()


    // <<< COROUTINES BAŞLANGIÇ >>>
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    // <<< COROUTINES SON >>>


    init {
        scope.launch {

            val a = dashboardInfoRepository.getUserInfoResponse(
                "5e063d198b9049075441ded8",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRW1haWwiOiJ0ZXN0MTFAdGVzdDExLmNvbSIsInVzZXJJZCI6IjVlMDYzZDE5OGI5MDQ5MDc1NDQxZGVkOCIsImlhdCI6MTU3NzQ3MTYwMywiZXhwIjoxNTc3NDc1MjAzfQ.lK6RjwHFYcIbyT_InWANi0fiOGaRUAQG0ML38MoJdOA"
            )

            try {
                a!!.let {
                    infoDataWith(
                        a.userFullName,
                        a.userFollowerCount,
                        a.userFollowedCount,
                        a.userSharedCount
                    )
                }
            } catch (e: Exception) {

            }


        }

    }


    fun infoDataWith(name: String, followerCount: Long, followedCount: Long, sharedCount: Long) {

        userFullName.value = name
        userFollowerCount.value = followerCount
        userFollowedCount.value = followedCount
        userSharedCount.value = sharedCount


    }


    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }

    fun cancelCoroutines() = coroutineContext.cancel()


}