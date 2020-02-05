package com.ardakazanci.anlikmotivasyon.repositories

import com.ardakazanci.anlikmotivasyon.data.model.DataModel
import com.ardakazanci.anlikmotivasyon.data.network.IApiInterface

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