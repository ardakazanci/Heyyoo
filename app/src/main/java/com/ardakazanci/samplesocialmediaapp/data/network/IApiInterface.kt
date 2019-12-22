package com.ardakazanci.samplesocialmediaapp.data.network

import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IApiInterface {

    @POST("/user/signUp")
    fun requestSignUp(@Body bodyData: DataModel.SignUpRequestModel): Deferred<Response<DataModel.SignUpResponseModel>>

}