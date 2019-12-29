package com.ardakazanci.samplesocialmediaapp.repositories

import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.IApiInterface

class FollowerListRepository(private val apiInterface: IApiInterface) : BaseRepository() {


    suspend fun getFollowerListInfo(
        userid: String
    ): List<DataModel.FollowerListModel>? {

        return safeApiCall(
            call = {
                apiInterface.requestFollowerListInfo(userid).await()
            },
            error = "FollowerListRepository : FollowerList işleminde bir hata oluştu."

        )?.userInfo

    }

}