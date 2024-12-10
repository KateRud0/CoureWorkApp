package com.example.courseworkapp.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseworkapp.R
import com.example.courseworkapp.ui.dialogs.SearchRoomDialog
import com.example.courseworkapp.viewmodel.RoomViewModel


class JoinedRoomsFragment : Fragment(R.layout.fragment_joined_rooms) {

    private lateinit var viewModel: RoomViewModel

    companion object {
        val roomAdapter: RoomAdapter = RoomAdapter(isHome = false, isCreatedRooms = false)
    }

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

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = roomAdapter

        //viewModel.listenToJoinedRooms()

        viewModel.joinedRooms.observe(viewLifecycleOwner) { rooms ->
            roomAdapter.updateRooms(rooms)
        }

        viewModel.getJoinedRooms()

        roomAdapter.onCopyCodeClick = { code ->
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("Room Code", code))
            Toast.makeText(requireContext(), "Code copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        roomAdapter.onOpenRoomClick = { roomId, roomName, connectionCode ->
            val action = R.id.action_joinedRooms_to_room
            val bundle = Bundle().apply {
                putString("roomId", roomId)          // Передача roomId
                putString("roomName", roomName)      // Передача имени комнаты
                putString("connectionCode", connectionCode) // Передача кода комнаты
                putBoolean("isOwner", false)
            }
            findNavController().navigate(action, bundle)
        }

        homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_joinedRooms_to_home)
        }

        searchRoomButton.setOnClickListener {
            val dialog = SearchRoomDialog()
            dialog.show(parentFragmentManager, "CreateRoomDialog")
        }
    }


}