package com.example.courseworkapp.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseworkapp.R
import com.example.courseworkapp.ui.adapters.RoomAdapter
import com.example.courseworkapp.ui.dialogs.AddRoomDialog
import com.example.courseworkapp.ui.dialogs.ChangeRoomNameDialog
import com.example.courseworkapp.viewmodel.RoomViewModel


class CreatedRoomsFragment : Fragment(R.layout.fragment_created_rooms) {

    private lateinit var viewModel: RoomViewModel
    //private lateinit var roomAdapter: RoomAdapter

    companion object {
       val roomAdapter: RoomAdapter = RoomAdapter(isHome = false, isCreatedRooms = true)
    }

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

        //roomAdapter = RoomAdapter(isHome = false, isCreatedRooms = true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = roomAdapter

        //viewModel.listenToCreatedRooms()

        viewModel.createdRooms.observe(viewLifecycleOwner) { rooms ->
            roomAdapter.updateRooms(rooms)
        }

        viewModel.getCreatedRooms()

        roomAdapter.onCopyCodeClick = { code ->
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("Room Code", code))
            Toast.makeText(requireContext(), "Code copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        roomAdapter.onOpenRoomClick = { roomId, roomName, connectionCode ->
            val action = R.id.action_createdRooms_to_room
            val bundle = Bundle().apply {
                putString("roomId", roomId)
                putString("roomName", roomName)
                putString("connectionCode", connectionCode)
                putBoolean("isOwner", true)
            }
            findNavController().navigate(action, bundle)
        }

        roomAdapter.onChangeCodeClick = { roomId ->
            val newCode = viewModel.generateConnectionCode()
            viewModel.updateRoomCode(roomId, newCode)
        }

        roomAdapter.onChangeRoomNameClick = { roomId, currentName ->
            val dialog = ChangeRoomNameDialog(roomId, currentName) { newName ->
                viewModel.updateRoomName(roomId, newName)
            }
            dialog.show(parentFragmentManager, "ChangeRoomNameDialog")
        }


        homeButton.setOnClickListener {
            findNavController().popBackStack()
        }

        addRoomButton.setOnClickListener {
            val dialog = AddRoomDialog()
            dialog.show(parentFragmentManager, "CreateRoomDialog")
        }

    }
}