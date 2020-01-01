package com.ardakazanci.samplesocialmediaapp.repositories

import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.IApiInterface

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