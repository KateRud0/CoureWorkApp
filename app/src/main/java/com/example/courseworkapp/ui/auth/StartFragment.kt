package com.example.courseworkapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.courseworkapp.R
import androidx.navigation.fragment.findNavController


class StartFragment : Fragment(R.layout.fragment_start) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Найти кнопки по их ID и установить обработчики нажатий
        view.findViewById<View>(R.id.buttonStartSingIn).setOnClickListener {
            // Переход на LoginFragment
            findNavController().navigate(R.id.action_start_to_login)
        }

        view.findViewById<View>(R.id.buttonStartSingUp).setOnClickListener {
            // Переход на RegisterFragment
            findNavController().navigate(R.id.action_start_to_register)

        }
    }

}