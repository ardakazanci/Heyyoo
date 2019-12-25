package com.ardakazanci.samplesocialmediaapp.data.network

import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Part

interface IApiInterface {

    @POST("/user/signUp")
    fun requestSignUp(@Body bodyData: DataModel.SignUpRequestModel): Deferred<Response<DataModel.SignUpResponseModel>>

    @POST("/user/login")
    fun requestSignIn(@Body bodyData: DataModel.SignInRequestModel): Deferred<Response<DataModel.SignInResponseModel>>
}