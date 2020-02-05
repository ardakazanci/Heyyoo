package com.ardakazanci.anlikmotivasyon.data.network

import com.ardakazanci.anlikmotivasyon.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Tüm uygulama boyunca tek bir örnek üzerinden kullanılması için Object kullandım.
object ApiService {

    // Retrofit İstemcisinin tutulacağı val değişkeni.
    private val apiClient = OkHttpClient().newBuilder().build()


    // Retrofit İstemcisinin Oluşturulması
    fun getRetrofit(): Retrofit {

        return Retrofit.Builder().client(apiClient)
            .baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    val mainApi: IApiInterface = getRetrofit().create(IApiInterface::class.java)

}