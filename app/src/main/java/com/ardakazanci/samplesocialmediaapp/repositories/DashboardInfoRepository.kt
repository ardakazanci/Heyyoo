package com.ardakazanci.samplesocialmediaapp.repositories

import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.IApiInterface

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