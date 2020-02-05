package com.ardakazanci.anlikmotivasyon.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

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

    data class FollowedResponse(
        val success: Boolean,
        val followedInfo: List<FollowedInfoModel>?
    )

    data class FollowedInfoModel(
        val _id: String,
        val followed: String
    )


    // LIKE İŞLEMİ İÇİN MODEL

    data class LikeResponse(
        @SerializedName("status")
        val status: Boolean
    )

    data class LikeRequest(
        @SerializedName("contentid")
        val contentid: String
    )


    // DISLIKE İŞLEMİ İÇİN MODEL

    data class DislikeResponse(
        @SerializedName("status")
        val status: Boolean
    )

    data class DislikeRequest(
        @SerializedName("contentid")
        val contentid: String
    )


    // USER SHARED CONTENT
    data class UserSharedContentResponse(
        @SerializedName("content")
        val content: List<ContentDataModel>
    )

    data class ContentDataModel(
        @SerializedName("contentCheck")
        val contentCheck: Boolean,
        @SerializedName("contentLikedCount")
        val contentLikedCount: Int,
        @SerializedName("contentNotLikeCount")
        val contentNotLikeCount: Int,
        @SerializedName("contentText")
        val contentText: String,
        @SerializedName("_id")
        var _id: String,
        @SerializedName("sharingDate")
        val sharingDate: Int,
        @SerializedName("sharingUserId")
        val sharingUserId: String,
        @SerializedName("sharingUserLocation")
        val sharingUserLocation: String,
        @SerializedName("__v")
        val v: Int
    )

    // Mesage Gönderme Modelleri
    data class MessageSendRequest(
        @SerializedName("message")
        val message: String,
        @SerializedName("senderId")
        val senderId: String,
        @SerializedName("receiverId")
        val receiverId: String,
        @SerializedName("date")
        val date: Long
    )

    data class MessageSendResponse(
        @SerializedName("success")
        val success: Boolean,
        @SerializedName("error")
        val error: Boolean
    )


    // Mesaj Listeleme Modelleri


    data class MessageGetRequestContainer(
        val senderId: String,
        val reciverId: String
    )

    data class MessageGetResponse(
        val message: String,
        val success: Boolean,
        val content: List<Message>,
        @SerializedName("userInfo")
        val userInfo: List<MessageGetUserInfo>

    )

    data class MessageGetUserInfo(
        val userFullName: String,
        val userImageBase64: String
    )

    // Kimlerle Mesajlaşılmış yada Kimden Mesaj gelmiş modelleri

    data class MessageConversationRequestModel(
        val currentId: String
    )


    data class MessageConversationResponseModel(
        @SerializedName("count")
        val count: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("success")
        val success: Boolean,
        @SerializedName("userInfo")
        val userInfo: List<MessageConversationUserModel>?
    )


    data class MessageConversationUserModel(
        @SerializedName("_id")
        val _id: String,
        @SerializedName("userFullName")
        val userFullName: String,
        @SerializedName("userImageBase64")
        val userImageBase64: String
    )


    data class Message(
        @SerializedName("messageSenderCode")
        val messageSenderCode: Int,
        @SerializedName("_id")
        val _id: String,
        @SerializedName("senderId")
        val senderId: String,
        @SerializedName("reciverId")
        val reciverId: String,
        @SerializedName("messageContent")
        val messageContent: String,
        @SerializedName("messageDate")
        val messageDate: Long
    )


}

// HOME SHARED İŞLEMLERİ
@Entity
data class Doc(
    @SerializedName("contentCheck")
    val contentCheck: Boolean,
    @SerializedName("contentLikedCount")
    val contentLikedCount: Int,
    @SerializedName("contentNotLikeCount")
    val contentNotLikeCount: Int,
    @SerializedName("contentText")
    val contentText: String,
    @SerializedName("_id")
    @PrimaryKey
    var _id: String,
    @SerializedName("sharingDate")
    val sharingDate: Int,
    @SerializedName("sharingUserId")
    val sharingUserId: String,
    @SerializedName("sharingUserLocation")
    val sharingUserLocation: String,
    @SerializedName("__v")
    val v: Int


)

data class ContentResponse(
    val content: Content
)

data class Content(
    val after: Int?,
    val before: Int?,
    val docs: List<Doc>,
    val hasNextPage: Boolean,
    val hasPrevPage: Boolean,
    val limit: Int,
    val page: Int,
    val pagingCounter: Int,
    val totalDocs: Int,
    val totalPages: Int
)


