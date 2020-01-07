package com.ardakazanci.samplesocialmediaapp


import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import com.ardakazanci.samplesocialmediaapp.ui.main.SocialMainActivity
import com.ardakazanci.samplesocialmediaapp.utils.Constants
import com.securepreferences.SecurePreferences


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Auto Login
        prefsUserTokenControl()

        // Transparan StatusBar ve BottomBar
        this.window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

    }


    private fun prefsUserTokenControl() {

        val preferences: SharedPreferences =
            SecurePreferences(
                this.applicationContext,
                Constants.PREF_USER_TOKEN_VALUE,
                Constants.PREF_USER_TOKEN
            )
        val userLoginTokenValue: String? =
            preferences.getString(Constants.PREF_USER_TOKEN_VALUE, null)


        if (!userLoginTokenValue.isNullOrBlank()) {
            Log.e("TokenKontrol", userLoginTokenValue.toString())
            val intent = Intent(
                this.applicationContext,
                SocialMainActivity::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        } else {
            Log.e("TokenKontrol", "Giriş yapılmamış")
        }


    }


}








