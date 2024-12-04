package com.example.courseworkapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class StartActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start) // Подключаем ваш макет activity_start.xml

        // Кнопки из XML
        val signInButton = findViewById<Button>(R.id.buttonSingIn)
        val signUpButton = findViewById<Button>(R.id.buttonSingUp)



        // Обработчик кнопки "Войти"
        signInButton.setOnClickListener {
            // Переход на экран авторизации
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        // Обработчик кнопки "Зарегистрироваться"
        signUpButton.setOnClickListener {
            // Переход на экран регистрации
            val intent = Intent(this, RegActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        // Проверка текущего пользователя
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Если пользователь уже вошел в систему, переходим на главный экран
            Log.d("Auth", "signIn:success email: ${auth.currentUser?.email}")

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Закрываем StartActivity
        }
        else {
            Log.d("Auth", "signIn:failure no current user")
        }
    }
}

