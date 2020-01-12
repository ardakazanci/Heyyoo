package com.ardakazanci.samplesocialmediaapp.repositories

import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.model.Doc
import com.ardakazanci.samplesocialmediaapp.data.network.IApiInterface

class ProfileSharedListRepository(private val api: IApiInterface) : BaseRepository() {


    suspend fun getUserSharedContent(
        userid: String
    ): List<DataModel.ContentDataModel>? {
        return safeApiCall(
            call = {
                api.fetchUserSharedContent(userid).await()
            },
            error = "Login Hata"
        )?.content


    }

}