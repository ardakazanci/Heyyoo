package com.ardakazanci.anlikmotivasyon.repositories

import com.ardakazanci.anlikmotivasyon.data.model.DataModel
import com.ardakazanci.anlikmotivasyon.data.network.IApiInterface

class MessageGetListRepository(private val api: IApiInterface) : BaseRepository() {

    suspend fun getMessageListResponse(
        senderId: String,
        reciverId: String
    ): List<DataModel.Message>? {
        return safeApiCall(
            call = {
                api.requestMessageGetList(
                    DataModel.MessageGetRequestContainer(senderId, reciverId)
                ).await()
            },
            error = "Dislike işleminde problem yaşandı"
        )?.content

    }


}