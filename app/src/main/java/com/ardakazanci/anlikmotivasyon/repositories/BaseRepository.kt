package com.ardakazanci.anlikmotivasyon.repositories

import android.util.Log
import com.ardakazanci.anlikmotivasyon.data.network.ApiOutput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

// Api Çağrılarının çalıştırılması için repository kullanılacak
open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, error: String): T? {

        return withContext(Dispatchers.IO) {

            val result = apiOutput(call, error)
            var output: T? = null
            when (result) {
                is ApiOutput.Success -> {
                    output = result.output
                }
                is ApiOutput.Error -> {
                    Log.e("Hata 1 :", "Bu $error ve bu ${result.exception}")

                }
            }
            output
        }


    }

    private suspend fun <T : Any> apiOutput(
        call: suspend () -> Response<T>,
        error: String
    ): ApiOutput<T> {
        val response = call.invoke()
        return withContext(Dispatchers.IO) {
            if (response.isSuccessful)
                ApiOutput.Success(response.body()!!)
            else
                ApiOutput.Error(IOException("Hata 2 :   $error"))

        }


    }

}