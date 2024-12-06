package com.example.courseworkapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
class SettingsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        auth = FirebaseAuth.getInstance()

        // Кнопка для возврата на главный экран
        val buttonGoBack = findViewById<ImageButton>(R.id.buttonGoBack)
        buttonGoBack.setOnClickListener {
            finish() // Закрываем SettingsActivity и возвращаемся на MainActivity
        }

        // Кнопка для выхода из аккаунта
        val buttonLogOut = findViewById<Button>(R.id.buttonLogOut)
        buttonLogOut.setOnClickListener {
            Log.d("Test", "LogOut: done")
            Toast.makeText(this, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show()
            auth.signOut() // Выход из аккаунта


            // Переход на экран авторизации
            val intent = Intent(this, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}