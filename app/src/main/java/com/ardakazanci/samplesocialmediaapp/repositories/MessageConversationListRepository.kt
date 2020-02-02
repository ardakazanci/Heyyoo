package com.ardakazanci.samplesocialmediaapp.repositories

import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.IApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageConversationListRepository(private val apiInterface: IApiInterface) :
    BaseRepository() {

    suspend fun getConversationList(
        currentId: String
    ): List<DataModel.MessageConversationUserModel>? {

        return withContext(Dispatchers.IO) {
            safeApiCall(
                call = {
                    apiInterface.requestConversationList(

                        currentId

                    ).await()
                },
                error = "MessagesConversationlist işleminde hata oluştu"
            )?.userInfo


        }


    }


}
