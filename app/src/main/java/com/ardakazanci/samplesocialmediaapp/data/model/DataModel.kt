package com.ardakazanci.samplesocialmediaapp.data.model

// API işlemleri için kullanılacak model - data verileri

object DataModel {

    // Kayıt olan kullanıcıdan istenecek bilgiler
    data class SignUpRequestModel(
        val userFullName: String,
        val userEmail: String,
        val userPassword: String
    )

    // Kayıt başarılıysa dönecek mesaj.
    data class SignUpResponseModel(var message: String)

}