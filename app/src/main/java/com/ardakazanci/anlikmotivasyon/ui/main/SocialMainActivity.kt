package com.ardakazanci.anlikmotivasyon.ui.main


import android.os.Bundle
import android.util.Log
import android.view.View


import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.ardakazanci.anlikmotivasyon.R
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class SocialMainActivity : AppCompatActivity() {

    private lateinit var parentLayout: View
    private var connectivityDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social_main)

        parentLayout = findViewById(R.id.container)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_messages,
                R.id.navigation_content,
                R.id.navigation_search,
                R.id.navigation_dashboard
            )
        )

        navView.setupWithNavController(navController)


        navView.setOnNavigationItemSelectedListener { item ->

            onNavDestinationSelected(
                item,
                Navigation.findNavController(this, R.id.nav_host_fragment)
            )

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("SocialMain", "Destroy oldu")
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

    override fun onPause() {
        super.onPause()
        safelyDispose(connectivityDisposable)
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
