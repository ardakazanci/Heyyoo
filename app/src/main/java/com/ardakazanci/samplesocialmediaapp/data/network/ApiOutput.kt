package com.ardakazanci.samplesocialmediaapp.data.network

// Enum tarzında Sealed class oluşturdum.
// Retrofit Response işlemleri bura fonksiyonlar ile değerlendirilecek
sealed class ApiOutput<out T : Any> {

    data class Success<out T : Any>(val output: T) : ApiOutput<T>()
    data class Error(val exception: Exception) : ApiOutput<Nothing>()

}