package com.ardakazanci.samplesocialmediaapp.ui.onboarding.signup

import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.network.ApiService
import com.ardakazanci.samplesocialmediaapp.repositories.SignUpRepository
import com.wajahatkarim3.easyvalidation.core.view_ktx.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


// ViewModel ile DataBinding Birleştirilecek

class SignUpViewModel : ViewModel() {

    // DataBinding Integration Implement
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

            userEmail.value!!.validEmail {
                _fieldEmptyControl.value = "Lütfen geçerli e-posta adresi giriniz."
            }
            userPassword.value!!.minLength(5) {
                _fieldEmptyControl.value = "Parolanız minimum 5 karakter olabilir."
            }
            userPassword.value!!.maxLength(15) {
                _fieldEmptyControl.value = "Parolanız maksimum 15 karakter olabilir."
            }
            userFullName.value!!.noSpecialCharacters {
                _fieldEmptyControl.value = "Adınız özel karakter içermemelidir."
            }
            userFullName.value!!.maxLength(20) { error_message ->
                _fieldEmptyControl.value = "Adınız maksimum 20 karakter olabilir."
            }

            signUpSuccess.value = true
            scope.launch {
                signUpRepository.getSignUpResponse(
                    userFullName.value!!,
                    userEmail.value!!,
                    userPassword.value!!
                )
            }

        } else {
            _fieldEmptyControl.value = "Lütfen boş alan bırakmayınız"
        }

    }

    // ViewModel onCleared Method with corutine finished !
    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }


    fun cancelRequests() = coroutineContext.cancel()
}