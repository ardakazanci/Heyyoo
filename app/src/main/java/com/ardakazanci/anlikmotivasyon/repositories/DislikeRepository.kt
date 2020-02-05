package com.ardakazanci.anlikmotivasyon.repositories

import com.ardakazanci.anlikmotivasyon.data.network.IApiInterface

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