package com.ardakazanci.samplesocialmediaapp.ui.onboarding.signup


import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ardakazanci.samplesocialmediaapp.data.network.ApiService
import com.ardakazanci.samplesocialmediaapp.repositories.SignUpRepository
import com.ardakazanci.samplesocialmediaapp.utils.SingleLiveEvent
import com.ardakazanci.samplesocialmediaapp.utils.UtilsFunctions
import com.wajahatkarim3.easyvalidation.core.view_ktx.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


// ViewModel ile DataBinding Birleştirilecek

class SignUpViewModel(private val app: Application) : AndroidViewModel(app) {


    // <<< FIELD YÜKLEMELERİ BAŞLANGIÇ >>>
    val userEmail = MutableLiveData<String>()
    val userPassword = MutableLiveData<String>()
    val userFullName = MutableLiveData<String>()


    val fieldUserImageUri = MutableLiveData<Uri>()

    private val _fieldEmptyControl = MutableLiveData<String>()
    val fieldEmptyControl: LiveData<String>
        get() = _fieldEmptyControl

    val signUpSuccess = SingleLiveEvent<Boolean>() // Kayıt başarı durumu

    private val signUpRepository: SignUpRepository = SignUpRepository(ApiService.mainApi)

    // <<< FIELD KONTROLLERİ SON >>>


    // <<< COROUTINES BAŞLANGIÇ >>>
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    // <<< COROUTINES SON >>>


    // <<< KAYIT OL BUTONUNA TIKLANINCA YAPILACAKLAR BAŞLANGIÇ >>>
    fun onSignUpButtonClick() {

        if (!userEmail.value.isNullOrBlank() && !userPassword.value.isNullOrEmpty() && !userFullName.value.isNullOrEmpty() && fieldUserImageUri.value != null) {

            if (fieldUserImageUri.value == null) {
                _fieldEmptyControl.value = "Lütfen profil fotoğrafınızı seçitiniz."
            }
            if (!userEmail.value!!.validEmail()) {
                _fieldEmptyControl.value = "Lütfen geçerli e-posta adresi giriniz."

            } else if (!userPassword.value!!.minLength(5)) {
                _fieldEmptyControl.value = "Parolanız minimum 5 karakter olabilir."

            } else if (!userPassword.value!!.maxLength(15)) {
                _fieldEmptyControl.value = "Parolanız maksimum 15 karakter olabilir."

            } else if (!userFullName.value!!.noSpecialCharacters()) {
                _fieldEmptyControl.value = "Adınız özel karakter içermemelidir."

            } else if (!userFullName.value!!.maxLength(20)) {
                _fieldEmptyControl.value = "Adınız maksimum 20 karakter olabilir."

            } else if (userEmail.value!!.isEmpty()) {
                _fieldEmptyControl.value = "E-Posta adresinizi giriniz."

            } else if (userPassword.value!!.isEmpty()) {
                _fieldEmptyControl.value = "Parolanızı giriniz."

            } else if (userFullName.value!!.isEmpty()) {
                _fieldEmptyControl.value = "Adınızı giriniz."

            } else {
                scope.launch {
                    val a = signUpRepository.getSignUpResponse(
                        userFullName.value!!, // String
                        userEmail.value!!,// String
                        userPassword.value!!, // String
                        UtilsFunctions.uriToBitmapFunction(fieldUserImageUri.value, app)
                    )
                    if (!a.toString().contains("Kayıt Başarılı")) {
                        _fieldEmptyControl.postValue("E-Posta adresi kullanımda.")
                    } else {
                        _fieldEmptyControl.postValue("Kayıt Başarılı")
                    }
                }
            }
        } else {
            _fieldEmptyControl.value = "Lütfen tüm alanları doldurunuz."
        }
    }
    // <<< KAYIT OL BUTONUNA TIKLANINCA YAPILACAKLAR SON >>>


    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }

    fun cancelCoroutines() = coroutineContext.cancel()


}