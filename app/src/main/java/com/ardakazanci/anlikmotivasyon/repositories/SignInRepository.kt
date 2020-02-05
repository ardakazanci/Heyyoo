package com.ardakazanci.anlikmotivasyon.repositories

import com.ardakazanci.anlikmotivasyon.data.model.DataModel
import com.ardakazanci.anlikmotivasyon.data.network.IApiInterface

class SignInRepository(private val apiInterface: IApiInterface) : BaseRepository() {


    suspend fun getSignInResponse(
        userEmail: String,
        userPassword: String
    ): String? {
        return safeApiCall(
            call = {
                apiInterface.requestSignIn(
                    DataModel.SignInRequestModel(
                        userEmail,
                        userPassword
                    )
                ).await()
            },
            error = "Login Hata"
        )?.token

    }

}