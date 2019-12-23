package com.ardakazanci.samplesocialmediaapp.repositories


import androidx.lifecycle.MutableLiveData
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.IApiInterface

class SignUpRepository(private val apiInterface: IApiInterface) : BaseRepository() {


    suspend fun getSignUpResponse(
        userFullName: String,
        userEmail: String,
        userPassword: String
    ): String? {
        return safeApiCall(
            call = {
                apiInterface.requestSignUp(
                    DataModel.SignUpRequestModel(
                        userFullName,
                        userEmail,
                        userPassword
                    )
                ).await()
            },
            error = "Hata"
        )?.message



    }

}