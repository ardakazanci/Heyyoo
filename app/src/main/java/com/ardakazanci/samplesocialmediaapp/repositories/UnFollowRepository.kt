package com.ardakazanci.samplesocialmediaapp.repositories

import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.IApiInterface

class UnFollowRepository(private val apiInterface: IApiInterface) : BaseRepository() {


    suspend fun getUnFollowResponse(
        userid: String,
        otheruserid: String
    ): Boolean? {
        return safeApiCall(
            call = {
                apiInterface.requesUnFollow(
                    userid,
                    DataModel.UnFollowRequestModel(
                        otheruserid
                    )
                ).await()
            },
            error = "Login Hata"
        )?.success

    }


}