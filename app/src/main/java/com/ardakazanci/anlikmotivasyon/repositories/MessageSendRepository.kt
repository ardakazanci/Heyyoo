package com.ardakazanci.anlikmotivasyon.repositories

import com.ardakazanci.anlikmotivasyon.data.model.DataModel
import com.ardakazanci.anlikmotivasyon.data.network.IApiInterface

class MessageSendRepository(private val api: IApiInterface) : BaseRepository() {

    suspend fun getMessageSendResponse(
        message: String,
        senderId: String,
        receiverId: String,
        date: Long
    ): Boolean? {
        return safeApiCall(
            call = {
                api.requestMessageSend(
                    DataModel.MessageSendRequest(
                        message,
                        senderId,
                        receiverId,
                        date
                    )
                ).await()
            },
            error = "Dislike işleminde problem yaşandı"
        )?.success

    }


}