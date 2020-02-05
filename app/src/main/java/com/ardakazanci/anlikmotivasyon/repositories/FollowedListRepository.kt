package com.ardakazanci.anlikmotivasyon.repositories

import com.ardakazanci.anlikmotivasyon.data.model.DataModel
import com.ardakazanci.anlikmotivasyon.data.network.IApiInterface

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