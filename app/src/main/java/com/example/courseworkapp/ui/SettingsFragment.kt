package com.example.courseworkapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.courseworkapp.R
import com.example.courseworkapp.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val userViewModel: UserViewModel by activityViewModels()

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val goBackButton = view.findViewById<ImageButton>(R.id.buttonGoBack)
        val logOutButton = view.findViewById<Button>(R.id.buttonLogOut)

        goBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_settings_to_home)
        }

        logOutButton.setOnClickListener {
            Log.d("Test", "LogOut: done")
            Toast.makeText(context, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show()
            auth.signOut()
            findNavController().navigate(R.id.auth_navigation)
        }

        val userNameTextView = view.findViewById<TextView>(R.id.textViewUserNameS)
        userViewModel.userName.observe(viewLifecycleOwner) { name ->
            userNameTextView.text = name
        }

    }
}