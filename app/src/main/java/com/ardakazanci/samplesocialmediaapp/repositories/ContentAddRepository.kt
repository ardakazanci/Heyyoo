package com.ardakazanci.samplesocialmediaapp.repositories

import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.IApiInterface

class ContentAddRepository(private val apiInterface: IApiInterface) : BaseRepository() {

    suspend fun getContentAddResponse(
        sharingUserId: String,
        sharingDate: Long,
        sharingUserLocation: String,
        contentText: String,
        authorization: String
    ): DataModel.responseContentShare? {

        return safeApiCall(

            call = {
                apiInterface.requestContentAdd(
                    DataModel.requestBodyContentShare(
                        sharingUserId,
                        sharingDate,
                        sharingUserLocation,
                        contentText
                    ),
                    authorization

                ).await()
            },
            error = "Content Add işlemi hatalı oldu"


        )

    }

}