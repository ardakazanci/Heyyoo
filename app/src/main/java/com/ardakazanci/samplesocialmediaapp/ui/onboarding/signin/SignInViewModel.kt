package com.ardakazanci.samplesocialmediaapp.ui.onboarding.signin

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ardakazanci.samplesocialmediaapp.data.network.ApiService
import com.ardakazanci.samplesocialmediaapp.repositories.SignInRepository
import com.ardakazanci.samplesocialmediaapp.utils.Constants
import com.ardakazanci.samplesocialmediaapp.utils.toast
import com.securepreferences.SecurePreferences
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class SignInViewModel(private val app: Application) : AndroidViewModel(app) {

    private val context = app.applicationContext

    var preferences: SharedPreferences =
        SecurePreferences(context, Constants.PREF_USER_TOKEN_VALUE, Constants.PREF_USER_TOKEN)
    var editor: SharedPreferences.Editor = preferences.edit()

    private val LOG_TAG: String = "SignInViewModel"


    // <<< DATABINDING FIELD BAŞLANGIÇ >>>
    val userEmailField = MutableLiveData<String>()
    val userPasswordField = MutableLiveData<String>()
    // <<< DATABINDING FIELD SON >>>

    private val _loginSuccessControl = MutableLiveData<Boolean>()
    val loginSucessControl: LiveData<Boolean>
        get() = _loginSuccessControl


    init {
        Log.i(LOG_TAG, "ViewModel Created")
    }

    // <<< COROUTINE İŞLEMLERİ BAŞLANGIÇ >>>
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    private val signInRepository: SignInRepository = SignInRepository(ApiService.mainApi)


    fun clickSignInButton() {
        Log.i(
            LOG_TAG,
            "Giriş yap tıklandı" + userEmailField.value.toString() + " " + userPasswordField.value.toString()
        )
        val userEmail = userEmailField.value.toString()
        val userPassword = userPasswordField.value.toString()

        if (!userEmail.isBlank() && !userPassword.isBlank()) {

            when {

                userPassword.isEmpty() -> context.toast("Parolanızı giriniz.")
                userEmail.isEmpty() -> context.toast("E-Posta adresinizi giriniz.")
                !userEmail.validEmail() -> context.toast("Geçerli mail adresi giriniz.")
                else -> scope.launch {
                    val loginProcess = signInRepository.getSignInResponse(
                        userEmail,
                        userPassword

                    )

                    if (loginProcess == null) {
                        _loginSuccessControl.postValue(false)
                    } else {
                        val token = loginProcess.toString()
                        editor.putString(Constants.PREF_USER_TOKEN_VALUE, token).apply()
                        _loginSuccessControl.postValue(true)

                    }

                }
            }


        } else {
            context.toast("Tüm alanları doldurunuz");
        }


    }


    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }

    fun cancelCoroutines() = coroutineContext.cancel()


}
