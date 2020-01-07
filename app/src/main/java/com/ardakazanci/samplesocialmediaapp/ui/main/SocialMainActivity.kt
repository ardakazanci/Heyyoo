package com.ardakazanci.samplesocialmediaapp.ui.main


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log


import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ardakazanci.samplesocialmediaapp.R
import com.ardakazanci.samplesocialmediaapp.ui.main.ui.content.ContentAddFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class SocialMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social_main)


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


    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("SocialMain", "Destroy oldu")
    }


}
