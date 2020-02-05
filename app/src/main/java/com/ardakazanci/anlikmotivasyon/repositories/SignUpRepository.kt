package com.ardakazanci.anlikmotivasyon.repositories


import com.ardakazanci.anlikmotivasyon.data.model.DataModel
import com.ardakazanci.anlikmotivasyon.data.network.IApiInterface

class SignUpRepository(private val apiInterface: IApiInterface) : BaseRepository() {


    suspend fun getSignUpResponse(
        userFullName: String,
        userEmail: String,
        userPassword: String,
        userImageBase64: String?
    ): String? {
        return safeApiCall(
            call = {
                apiInterface.requestSignUp(
                    DataModel.SignUpRequestModel(
                        userFullName,
                        userEmail,
                        userPassword,
                        userImageBase64
                    )
                ).await()
            },
            error = "Hata"
        )?.message


    }

}