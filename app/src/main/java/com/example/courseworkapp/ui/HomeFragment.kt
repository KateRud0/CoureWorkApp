package com.example.courseworkapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.courseworkapp.R
import androidx.navigation.fragment.findNavController


class HomeFragment : Fragment(R.layout.fragment_home) {

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

        settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_settings)
        }

        CreatedRoomsButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_createdRooms)
        }

        JoinedRoomsButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_joinedRooms)
        }

    }

}