package com.ardakazanci.anlikmotivasyon.repositories

import com.ardakazanci.anlikmotivasyon.data.model.DataModel
import com.ardakazanci.anlikmotivasyon.data.network.IApiInterface

class DashboardInfoRepository(private val apiInterface: IApiInterface) : BaseRepository() {

    suspend fun getUserInfoResponse(
        userid: String,
        Authorization: String
    ): DataModel.ProfileInfoUserResponseModel? {
        return safeApiCall(
            call = {
                apiInterface.requestProfileInfo(
                    userid, Authorization
                ).await()
            },
            error = "Login Hata"
        )?.usercollections

    }


}