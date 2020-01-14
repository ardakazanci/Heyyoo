package com.ardakazanci.samplesocialmediaapp.repositories

import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.IApiInterface

class DislikeRepository(private val apiInterface: IApiInterface) : BaseRepository() {


    suspend fun getDislikeResponse(
        contentid: String
    ): Boolean? {
        return safeApiCall(
            call = {
                apiInterface.requestDislike(contentid).await()
            },
            error = "Dislike işleminde problem yaşandı"
        )?.status

    }


}