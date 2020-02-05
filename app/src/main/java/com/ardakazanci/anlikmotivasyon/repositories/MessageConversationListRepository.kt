package com.ardakazanci.anlikmotivasyon.repositories

import com.ardakazanci.anlikmotivasyon.data.model.DataModel
import com.ardakazanci.anlikmotivasyon.data.network.IApiInterface
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
