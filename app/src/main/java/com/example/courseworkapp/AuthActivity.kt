package com.example.courseworkapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth


class AuthActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Настройка навигации
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.authNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
    }

    override fun onStart() {
        super.onStart()
        // Проверка текущего пользователя
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Если пользователь уже вошел в систему, переходим на главный экран
            Log.d("Test", "signIn:success email: ${auth.currentUser?.email}")

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Закрываем StartActivity
        } else {
            Log.d("Test", "signIn:failure no current user")
        }
    }
}


