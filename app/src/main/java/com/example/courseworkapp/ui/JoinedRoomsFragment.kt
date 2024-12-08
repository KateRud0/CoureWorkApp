package com.example.courseworkapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.courseworkapp.R
import com.example.courseworkapp.ui.dialogs.SearchRoomDialog


class JoinedRoomsFragment : Fragment(R.layout.fragment_joined_rooms) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeButton = view.findViewById<ImageButton>(R.id.buttonGoHomeJR)
        val searchRoomButton = view.findViewById<Button>(R.id.buttonSearchRoomJR)

        homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_joinedRooms_to_home)
        }

        searchRoomButton.setOnClickListener {
            val dialog = SearchRoomDialog()
            dialog.show(parentFragmentManager, "CreateRoomDialog")
        }
    }


}