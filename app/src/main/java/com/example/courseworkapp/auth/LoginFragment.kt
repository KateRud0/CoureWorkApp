package com.example.courseworkapp.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.courseworkapp.R
import com.google.firebase.auth.FirebaseAuth
import com.example.courseworkapp.MainActivity

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val emailEditText = view.findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = view.findViewById<EditText>(R.id.editTextPassword)
        val loginButton = view.findViewById<Button>(R.id.buttonSingIn)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(context, "Введите email и пароль", Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Авторизация успешна
                    Toast.makeText(context, "Вход выполнен!", Toast.LENGTH_SHORT).show()
                    Log.d("Test", "signInWithEmail:success")
                    val navController = findNavController()
                    navController.navigate(R.id.action_login_to_home)

                } else {
                    // Ошибка авторизации
                    Toast.makeText(context, "Неправильно введены имя пользователя или пароль.", Toast.LENGTH_SHORT).show()
                    Log.w("Test", "signInWithEmail:failure", task.exception)
                }
            }

    }
}

