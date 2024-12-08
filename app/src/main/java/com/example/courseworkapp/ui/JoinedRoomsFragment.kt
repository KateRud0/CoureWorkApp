package com.example.courseworkapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.courseworkapp.R


class JoinedRoomsFragment : Fragment(R.layout.fragment_joined_rooms) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeButton = view.findViewById<ImageButton>(R.id.buttonGoHomeJR)

        homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_joinedRooms_to_home)
        }
    }


}