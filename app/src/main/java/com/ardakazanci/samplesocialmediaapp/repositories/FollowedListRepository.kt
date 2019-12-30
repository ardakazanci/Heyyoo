package com.ardakazanci.samplesocialmediaapp.repositories

import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.IApiInterface

class FollowedListRepository(private val apiInterface: IApiInterface) : BaseRepository() {

    suspend fun getFollowedListModel(
        userid: String
    ): List<DataModel.FollowedListModel>? {

        return safeApiCall(
            call = {
                apiInterface.requestFollowedListInfo(userid).await()
            },
            error = "FollowedListInfo : FollowedList işleminde bir hata oluştu."

        )?.userInfo

    }

}