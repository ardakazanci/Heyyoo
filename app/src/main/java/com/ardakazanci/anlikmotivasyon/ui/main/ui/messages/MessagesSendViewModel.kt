package com.ardakazanci.anlikmotivasyon.ui.main.ui.messages

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ardakazanci.anlikmotivasyon.data.model.DataModel
import com.ardakazanci.anlikmotivasyon.data.network.ApiService
import com.ardakazanci.anlikmotivasyon.repositories.MessageGetListRepository
import com.ardakazanci.anlikmotivasyon.repositories.MessageSendRepository
import com.ardakazanci.anlikmotivasyon.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MessagesSendViewModel(private val app: Application) : AndroidViewModel(app) {

    private val LOG_TAG: String = "MessageSendVM"
    private val mContext: Context = app.applicationContext

    private val repository: MessageSendRepository = MessageSendRepository(ApiService.mainApi)
    private val messageListRepository: MessageGetListRepository =
        MessageGetListRepository(ApiService.mainApi)


    val messageListInfo = MutableLiveData<List<DataModel.Message>>()

    val currentUserId = MutableLiveData<String>()
    val otherUserId = MutableLiveData<String>()
    val messageText = MutableLiveData<String>()


    // <<< COROUTINES BAŞLANGIÇ >>>
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO
    private val scope = CoroutineScope(coroutineContext)
    // <<< COROUTINES SON >>>

    init {


    }


    fun getMessageList(currentUserId: String, otherUserId: String) {

        if (currentUserId.isBlank() || otherUserId.isBlank()) {
            Log.e(LOG_TAG, "Boş geldi")
        } else {


            scope.launch {

                val b = messageListRepository.getMessageListResponse(currentUserId, otherUserId)
                try {
                    b!!.let {

                        messageListInfo.postValue(it)

                    }
                } catch (e: Exception) {
                    Log.e(LOG_TAG, "Boş mesaj geldi")
                }



            }


        }

    }

    fun messageSendFunc() {


        val unixTime = System.currentTimeMillis() / 1000L
        currentUserId.value!!.let { currentUser ->
            otherUserId.value!!.let { otherUser ->
                messageText.value!!.let { messageText ->

                    scope.launch {


                        val a = repository.getMessageSendResponse(
                            messageText,
                            currentUser,
                            otherUser,
                            unixTime
                        )

                        a!!.let { success ->

                            if (success == true) {

                                // Mesage Gönderme Başarılı

                            } else {

                                mContext.toast("Mesaj gönderilemedi")

                            }

                        }
                    }


                }


            }


        }


    }

    override fun onCleared() {
        super.onCleared()

    }


}
