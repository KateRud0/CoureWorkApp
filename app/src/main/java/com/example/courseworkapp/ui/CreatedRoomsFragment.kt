package com.example.courseworkapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseworkapp.R
import com.example.courseworkapp.data.RoomStyle
import com.example.courseworkapp.ui.dialogs.AddRoomDialog
import com.example.courseworkapp.viewmodel.RoomViewModel

class CreatedRoomsFragment : Fragment(R.layout.fragment_created_rooms) {

    private lateinit var viewModel: RoomViewModel
    private lateinit var roomAdapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeButton = view.findViewById<ImageButton>(R.id.buttonGoHomeCR)
        val addRoomButton = view.findViewById<Button>(R.id.buttonAddRoomCR)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewCreatedRooms)

        viewModel = ViewModelProvider(this).get(RoomViewModel::class.java)

        roomAdapter = RoomAdapter(isHome = false, isCreatedRooms = true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = roomAdapter

        //viewModel.listenToCreatedRooms()

        viewModel.createdRooms.observe(viewLifecycleOwner) { rooms ->
            roomAdapter.updateRooms(rooms)

            viewModel.getCreatedRooms()

            homeButton.setOnClickListener {
                findNavController().navigate(R.id.action_createdRooms_to_home)
            }

            addRoomButton.setOnClickListener {
                val dialog = AddRoomDialog()
                dialog.show(parentFragmentManager, "CreateRoomDialog")
            }
        }
    }
}