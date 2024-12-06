package com.example.courseworkapp.auth

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


class RegFragment : Fragment(R.layout.fragment_reg) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val emailEditText = view.findViewById<EditText>(R.id.editTextEmail)
        val usernameEditText = view.findViewById<EditText>(R.id.editTextUserName)
        val passwordEditText = view.findViewById<EditText>(R.id.editTextPassword)
        val repeatPasswordEditText = view.findViewById<EditText>(R.id.editTextRepPassword)
        val registerButton = view.findViewById<Button>(R.id.buttonSingUp)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val repeatPassword = repeatPasswordEditText.text.toString().trim()

            if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()) {
                if (password == repeatPassword) {
                    registerUser(email, password, username)
                } else {
                    Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Test", "createUserWithEmail:success")
                    Toast.makeText(requireContext(), "Регистрация успешна!", Toast.LENGTH_SHORT).show()
                    navigateToLoginFragment()
                } else {
                    Log.w("Test", "createUserWithEmail:failure", task.exception)
                }
            }
    }

    private fun navigateToLoginFragment() {
        val navController = requireActivity()
            .supportFragmentManager
            .findFragmentById(R.id.mainNavHostFragment)?.findNavController()
        navController?.navigate(R.id.action_registerFragment_to_loginFragment)
    }
}