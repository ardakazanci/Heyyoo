package com.ardakazanci.samplesocialmediaapp.ui.onboarding.signup

import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.ApiOutput
import com.ardakazanci.samplesocialmediaapp.data.network.ApiService
import com.ardakazanci.samplesocialmediaapp.repositories.SignUpRepository
import com.wajahatkarim3.easyvalidation.core.view_ktx.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


// ViewModel ile DataBinding Birleştirilecek

class SignUpViewModel : ViewModel() {

    // DataBin
    val userEmail = MutableLiveData<String>()
    val userPassword = MutableLiveData<String>()
    val userFullName = MutableLiveData<String>()

    // Edittext Field Control
    private val _fieldEmptyControl = MutableLiveData<String>()
    val fieldEmptyControl: LiveData<String>
        get() = _fieldEmptyControl


    // Coroutines Control Intiliaze
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val signUpRepository: SignUpRepository = SignUpRepository(ApiService.mainApi)
    val signUpSuccess = MutableLiveData<Boolean>()


    // SignUp Button Click Control
    fun onSignUpButtonClick() {
        if (!userEmail.value.isNullOrBlank() && !userPassword.value.isNullOrEmpty() && !userFullName.value.isNullOrEmpty()) {

            if (!userEmail.value!!.validEmail()) {
                _fieldEmptyControl.value = "Lütfen geçerli e-posta adresi giriniz."
                signUpSuccess.value = false
            } else if (!userPassword.value!!.minLength(5)) {
                _fieldEmptyControl.value = "Parolanız minimum 5 karakter olabilir."
                signUpSuccess.value = false

            } else if (!userPassword.value!!.maxLength(15)) {
                _fieldEmptyControl.value = "Parolanız maksimum 15 karakter olabilir."
                signUpSuccess.value = false
            } else if (!userFullName.value!!.noSpecialCharacters()) {
                _fieldEmptyControl.value = "Adınız özel karakter içermemelidir."
                signUpSuccess.value = false
            } else if (!userFullName.value!!.maxLength(20)) {
                _fieldEmptyControl.value = "Adınız maksimum 20 karakter olabilir."
                signUpSuccess.value = false
            } else if (userEmail.value!!.length == 0) {
                _fieldEmptyControl.value = "E-Posta adresinizi giriniz."
                signUpSuccess.value = false
            } else if (userPassword.value!!.length == 0) {
                _fieldEmptyControl.value = "Parolanızı giriniz."
                signUpSuccess.value = false
            } else if (userFullName.value!!.length == 0) {
                _fieldEmptyControl.value = "Adınızı giriniz."
                signUpSuccess.value = false
            } else {
                Log.i("Kayıt Durumu", "Kayıt Başarılı")

                scope.launch {

                    val a = signUpRepository.getSignUpResponse(
                        userFullName.value!!,
                        userEmail.value!!,
                        userPassword.value!!
                    )

                    if (!a.toString().contains("Kayıt Başarılı")) {
                        _fieldEmptyControl.postValue("E-Posta adresi kullanımda.")
                        signUpSuccess.postValue(false)
                    } else {
                        signUpSuccess.postValue(true)
                    }


                }
            }

        } else {
            _fieldEmptyControl.value = "Lütfen tüm alanları doldurunuz."
        }


    }

    // ViewModel onCleared Method with corutine finished !
    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()

    }


    fun cancelRequests() = coroutineContext.cancel()
}