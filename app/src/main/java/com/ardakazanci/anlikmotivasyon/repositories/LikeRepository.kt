package com.ardakazanci.anlikmotivasyon.repositories

import com.ardakazanci.anlikmotivasyon.data.network.IApiInterface

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