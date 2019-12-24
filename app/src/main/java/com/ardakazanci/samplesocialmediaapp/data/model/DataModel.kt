package com.ardakazanci.samplesocialmediaapp.data.model

import java.io.File

// API işlemleri için kullanılacak model - data verileri

object DataModel {

    // Kayıt olan kullanıcıdan istenecek bilgiler
    data class SignUpRequestModel(
        val userFullName: String,
        val userEmail: String,
        val userPassword: String,
        val userImageBase64: String?
    )

    // Kayıt başarılıysa dönecek mesaj.
    data class SignUpResponseModel(var message: String, var error: String?, var success: String)

}