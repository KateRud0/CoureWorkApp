package com.example.courseworkapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {
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
}

