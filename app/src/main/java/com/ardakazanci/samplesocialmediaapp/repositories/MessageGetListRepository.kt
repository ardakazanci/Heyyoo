package com.ardakazanci.samplesocialmediaapp.repositories

import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.IApiInterface

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