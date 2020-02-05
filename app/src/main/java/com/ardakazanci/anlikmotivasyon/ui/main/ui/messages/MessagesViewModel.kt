package com.ardakazanci.anlikmotivasyon.ui.main.ui.messages

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.ardakazanci.anlikmotivasyon.data.model.DataModel
import com.ardakazanci.anlikmotivasyon.data.network.ApiService
import com.ardakazanci.anlikmotivasyon.repositories.MessageConversationListRepository
import com.ardakazanci.anlikmotivasyon.utils.Constants
import com.securepreferences.SecurePreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MessagesViewModel(private val app: Application) : AndroidViewModel(app) {


    private var LOG_TAG = "MessagesVM"
    private var mContext: Context = app.applicationContext

    private val _conversationList = MutableLiveData<List<DataModel.MessageConversationUserModel>>()
    val conversationList: LiveData<List<DataModel.MessageConversationUserModel>>
        get() = _conversationList

    private val _visibleControl = MutableLiveData<Boolean>()
    val visibleControl: LiveData<Boolean>
        get() = _visibleControl


    val preferences: SharedPreferences =
        SecurePreferences(
            mContext,
            Constants.PREF_USER_TOKEN_VALUE,
            Constants.PREF_USER_TOKEN
        )

    val currentId: String? = preferences.getString(Constants.PREF_USER_ID_VALUE, null)


    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        getConversationList()
    }


    val repository: MessageConversationListRepository =
        MessageConversationListRepository(ApiService.mainApi)


    fun getConversationList() {

        viewModelScope.launch {

            if (currentId != null) {

                try {
                    Log.e(LOG_TAG, currentId)
                    val a = repository.getConversationList(currentId)


                    if (a != null) {
                        _conversationList.postValue(a)
                        _visibleControl.postValue(true)
                    } else {
                        Log.e(LOG_TAG, "Repository null döndü")
                        _visibleControl.postValue(false)
                    }


                } catch (e: Exception) {

                    Log.e(LOG_TAG, "Catch kısmı çalıştı" + e.printStackTrace().toString())

                }

            } else {

                Log.e(LOG_TAG, "CurrentID null geldi")
            }

        }

    }

    fun coroutinesClear() = coroutineContext.cancel()


    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }


}
