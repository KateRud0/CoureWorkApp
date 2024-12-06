package com.example.courseworkapp

import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.courseworkapp.R
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        /* // Кнопка для перехода на настройки
        val buttonSettings = findViewById<ImageButton>(R.id.buttonSettings)
        buttonSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }*/

        // Настройка NavHostFragment


    }


    override fun onStart() {
        super.onStart()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            // Если пользователь авторизован, идем в MainNavGraph
            Log.d("Test", "signIn:success email: ${auth.currentUser?.email}")
            navController.navigate(R.id.main_navigation)


        } else {
            Log.d("Test", "signIn:failure no current user")
            navController.navigate(R.id.auth_navigation)
        }
    }
}