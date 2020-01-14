package com.ardakazanci.samplesocialmediaapp.repositories

import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.IApiInterface

class LikeRepository(private val apiInterface: IApiInterface) : BaseRepository() {


    suspend fun getLikeResponse(
        contentid: String
    ): Boolean? {
        return safeApiCall(
            call = {
                apiInterface.requestLike(contentid).await()
            },
            error = "Dislike işleminde problem yaşandı"
        )?.status

    }


}