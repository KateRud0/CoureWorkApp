package com.example.courseworkapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseworkapp.R
import com.example.courseworkapp.ui.dialogs.SearchRoomDialog
import com.example.courseworkapp.viewmodel.RoomViewModel


class JoinedRoomsFragment : Fragment(R.layout.fragment_joined_rooms) {

    private lateinit var viewModel: RoomViewModel
    private lateinit var roomAdapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeButton = view.findViewById<ImageButton>(R.id.buttonGoHomeJR)
        val searchRoomButton = view.findViewById<Button>(R.id.buttonSearchRoomJR)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewJoinedRooms)

        viewModel = ViewModelProvider(this).get(RoomViewModel::class.java)

        roomAdapter = RoomAdapter(isHome = false, isCreatedRooms = false)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = roomAdapter

        //viewModel.listenToJoinedRooms()

        viewModel.joinedRooms.observe(viewLifecycleOwner) { rooms ->
            roomAdapter.updateRooms(rooms)
        }

        viewModel.getJoinedRooms()

        homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_joinedRooms_to_home)
        }

        searchRoomButton.setOnClickListener {
            val dialog = SearchRoomDialog()
            dialog.show(parentFragmentManager, "CreateRoomDialog")
        }
    }


}