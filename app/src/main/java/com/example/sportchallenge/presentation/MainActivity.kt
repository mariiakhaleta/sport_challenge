package com.example.sportchallenge.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sportchallenge.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
                    as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
        bottomNavigationView.setupWithNavController(navController)

        val data: Uri? = intent?.data
        Log.d("URI", data.toString())
        if (data != null) {
            val bundle = Bundle()
            bundle.putString("code", intent?.data?.getQueryParameter("code"))
            navController.navigate(R.id.navigation_settings, bundle)
        }
        // http://localhost?code=c1869ff9f9594721b79fdabdf166f0b2985bad03&scope=activity%3Awrite%2Cread
    }
}