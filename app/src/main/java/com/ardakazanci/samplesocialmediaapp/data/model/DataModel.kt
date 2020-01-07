package com.ardakazanci.samplesocialmediaapp.data.model

import com.google.gson.annotations.SerializedName
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


    // FOLLOWER LİSTELEME VERİ MODELİ
    // RESPONSE İÇİN
    data class FollowerListResponse(
        var userInfo: List<FollowerListModel>
    )

    data class FollowerListModel(
        var _id: String,
        var followerCount: Long,
        var userFullName: String,
        var userImageBase64: String
    )

    // FOLLOWED LİSTELEME VERİ MODELİ
    // RESPONSE İÇİN
    data class FollowedListResponse(
        var userInfo: List<FollowedListModel>
    )

    data class FollowedListModel(
        var _id: String,
        var userFullName: String,
        var userImageBase64: String
    )


    // UNFOLLOW İÇİN
    data class UnFollowRequestModel(
        val otheruserid: String

    )

    data class UnFollowResponseModel(
        val success: Boolean,
        val message: String
    )

    // FOLLOW İÇİN
    data class FollowRequestModel(
        val userid: String
    )

    data class FollowResponseModel(
        val success: Boolean,
        val message: String
    )

    // ALGOLIA
    data class AlgoliaUserResponseModel(
        @SerializedName("objectID")
        val _id: String,
        val userFullName: String,
        val userImageBase64: String
    )

    // CONTENT SHARE
    data class requestBodyContentShare(
        val sharingUserId: String,
        val sharingDate: Long,
        val sharingUserLocation: String,
        val contentText: String
    )

    data class responseContentShare(
        val message: String,
        val error: String?,
        val success: Boolean

    )


}