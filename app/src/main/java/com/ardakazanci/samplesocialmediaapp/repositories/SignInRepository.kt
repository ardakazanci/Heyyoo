package com.ardakazanci.samplesocialmediaapp.repositories

import android.util.Log
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.IApiInterface

class SignInRepository(private val apiInterface: IApiInterface) : BaseRepository() {


    suspend fun getSignInResponse(
        userEmail: String,
        userPassword: String
    ): String? {
        return safeApiCall(
            call = {
                Log.e("Repository", userEmail + " " + userPassword)
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