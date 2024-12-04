package com.example.courseworkapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.userProfileChangeRequest

class RegActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg) // Подключаем макет для регистрации
        /*
        // Инициализация FirebaseAuth
        auth = Firebase.auth

        // Поля ввода и кнопка из XML
        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val usernameEditText = findViewById<EditText>(R.id.editTextUserName)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val repeatPasswordEditText = findViewById<EditText>(R.id.editTextRepPassword)
        val registerButton = findViewById<Button>(R.id.buttonSingUp)

        // Обработчик нажатия кнопки "Зарегистрироваться"
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val repeatPassword = repeatPasswordEditText.text.toString().trim()

            if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()) {
                if (password == repeatPassword) {
                    registerUser(email, password, username)
                } else {
                    Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Успешная регистрация
                    Log.d("RegActivity", "createUserWithEmail:success")
                    val user = auth.currentUser
                    user?.let {
                        updateUserProfile(it, username)
                    }
                } else {
                    // Ошибка регистрации
                    Log.w("RegActivity", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        this, "Ошибка регистрации: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun updateUserProfile(user: FirebaseAuth, username: String) {
        val profileUpdates = userProfileChangeRequest {
            displayName = username
        }

        user.currentUser?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("RegisterActivity", "User profile updated.")
                    Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show()
                    finish() // Закрываем экран регистрации
                } else {
                    Log.w("RegisterActivity", "Profile update failed", task.exception)
                    Toast.makeText(
                        this, "Ошибка обновления профиля: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
    */
    }
}
