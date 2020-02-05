package com.ardakazanci.anlikmotivasyon.repositories

import com.ardakazanci.anlikmotivasyon.data.model.DataModel
import com.ardakazanci.anlikmotivasyon.data.network.IApiInterface

class FollowRepository(private val apiInterface: IApiInterface) : BaseRepository() {


    suspend fun getFollowResponse(
        otheruserid: String,
        userid: String
    ): Boolean? {
        return safeApiCall(
            call = {
                apiInterface.requestFollow(
                    otheruserid,
                    DataModel.FollowRequestModel(
                        userid
                    )
                ).await()
            },
            error = "Login Hata"
        )?.success

    }


}