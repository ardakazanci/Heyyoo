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
import com.ardakazanci.samplesocialmediaapp.utils.toast
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.google.android.material.snackbar.Snackbar
import com.securepreferences.SecurePreferences
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {


    private var connectivityDisposable: Disposable? = null
    private var internetDisposable: Disposable? = null
    private lateinit var parentLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        parentLayout = findViewById(R.id.activity_main)

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

    override fun onResume() {
        super.onResume()
        networkConnectionControl()
    }

    private fun networkConnectionControl() {

        connectivityDisposable = ReactiveNetwork.observeNetworkConnectivity(applicationContext)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { connectivity ->
                Log.d("MainActivity", connectivity.toString())
                val state = connectivity.state()

                if (state.toString().equals("DISCONNECTED")) {
                    snackbarShow(parentLayout)
                }


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


    override fun onPause() {
        super.onPause()
        safelyDispose(connectivityDisposable)
        safelyDispose(internetDisposable)
    }

    private fun safelyDispose(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

    private fun snackbarShow(view: View) {
        Snackbar.make(
            view,
            getString(R.string.network_connection_error),
            Snackbar.LENGTH_LONG
        ).show()
    }


}








