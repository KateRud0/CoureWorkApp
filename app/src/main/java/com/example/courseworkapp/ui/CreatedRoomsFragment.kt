package com.example.courseworkapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.courseworkapp.R
import com.example.courseworkapp.ui.dialogs.AddRoomDialog

class CreatedRoomsFragment : Fragment(R.layout.fragment_created_rooms) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeButton = view.findViewById<ImageButton>(R.id.buttonGoHomeCR)
        val addRoomButton = view.findViewById<Button>(R.id.buttonAddRoomCR)

        homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_createdRooms_to_home)
        }

        addRoomButton.setOnClickListener {
            val dialog = AddRoomDialog()
            dialog.show(parentFragmentManager, "CreateRoomDialog")
        }
    }
}