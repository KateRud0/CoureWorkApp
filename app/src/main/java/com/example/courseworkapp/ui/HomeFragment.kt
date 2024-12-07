package com.example.courseworkapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.courseworkapp.R
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.courseworkapp.viewmodel.UserViewModel


class HomeFragment : Fragment(R.layout.fragment_home) {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val userViewModel: UserViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settingsButton = view.findViewById<ImageButton>(R.id.buttonSettings)
        val CreatedRoomsButton = view.findViewById<ImageButton>(R.id.buttonCreatedRooms)
        val JoinedRoomsButton = view.findViewById<ImageButton>(R.id.buttonJoinedRooms)

        val userNameTextView = view.findViewById<TextView>(R.id.textViewUserName)

        settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_settings)
        }

        CreatedRoomsButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_createdRooms)
        }

        JoinedRoomsButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_joinedRooms)
        }

        val currentUser = auth.currentUser
        if (currentUser != null) {
            db.collection("users").document(currentUser.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val username = document.getString("name") ?: "Пользователь"
                        userViewModel.setUserName(username)
                        userNameTextView.text = username
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("FirestoreError", "Error fetching user data", e)
                }
        }

    }

}