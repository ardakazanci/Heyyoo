package com.ardakazanci.samplesocialmediaapp.data.network

import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface IApiInterface {

    @POST("/user/signUp")
    fun requestSignUp(@Body bodyData: DataModel.SignUpRequestModel): Deferred<Response<DataModel.SignUpResponseModel>>

    @POST("/user/login")
    fun requestSignIn(@Body bodyData: DataModel.SignInRequestModel): Deferred<Response<DataModel.SignInResponseModel>>


    @GET("/user/{userid}")
    fun requestProfileInfo(
        @Path("userid") userid: String,
        @Header("Authorization") auth: String
    ): Deferred<Response<DataModel.ProfileInfoResponseModel>>
}

