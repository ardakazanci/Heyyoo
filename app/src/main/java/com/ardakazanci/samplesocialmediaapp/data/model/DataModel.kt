package com.ardakazanci.samplesocialmediaapp.data.model

// API işlemleri için kullanılacak model - data verileri

object DataModel {

    data class SignUpRequestModel(
        val userFullName: String,
        val userEmail: String,
        val userPassword: String
    )

    data class SignUpResponseModel(var messaage: String)

}