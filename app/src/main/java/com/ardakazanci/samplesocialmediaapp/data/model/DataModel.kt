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

    data class SignInRequestModel(
        val userEmail: String,
        val userPassword: String
    )


    // <<< PROFIL BİLGİLERİ BAŞLANGIÇ >>>

    data class ProfileInfoResponseModel(
        val usercollections: ProfileInfoUserResponseModel
    )

    data class ProfileInfoUserResponseModel(
        val userFollowerCount: Long,
        val userFollowedCount: Long,
        val userSharedCount: Long,
        val userFullName: String,
        val _id: String,
        val userEmail: String,
        val userImageBase64: String
    )

    // <<< PROFIL BİLGİLERİ SON >>>

    // Kayıt başarılıysa dönecek mesaj.
    data class SignUpResponseModel(var message: String, var error: String?, var success: String)

    data class SignInResponseModel(var token: String?, var message: String)
}