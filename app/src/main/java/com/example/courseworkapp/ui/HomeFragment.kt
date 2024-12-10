package com.example.courseworkapp.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.courseworkapp.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseworkapp.data.RoomStyle
import com.example.courseworkapp.ui.dialogs.AddRoomDialog
import com.example.courseworkapp.ui.dialogs.ChangeRoomNameDialog
import com.example.courseworkapp.ui.dialogs.SearchRoomDialog
import com.example.courseworkapp.viewmodel.RoomViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.courseworkapp.viewmodel.UserViewModel


class HomeFragment : Fragment(R.layout.fragment_home) {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val userViewModel: UserViewModel by activityViewModels()

    private lateinit var viewModel: RoomViewModel
    private lateinit var createdRoomsAdapter: RoomAdapter
    private lateinit var joinedRoomsAdapter: RoomAdapter
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
        val addRoomButton = view.findViewById<Button>(R.id.buttonAddRoomH)
        val searchRoomButton = view.findViewById<Button>(R.id.buttonSearchRoomH)

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

        addRoomButton.setOnClickListener {
            val dialog = AddRoomDialog()
            dialog.show(parentFragmentManager, "CreateRoomDialog")
        }

        searchRoomButton.setOnClickListener {
            val dialog = SearchRoomDialog()
            dialog.show(parentFragmentManager, "CreateRoomDialog")
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

        val createdRoomsRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewCreatedRoomsHome)
        val joinedRoomsRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewJoinedRoomsHome)

        viewModel = ViewModelProvider(this).get(RoomViewModel::class.java)

        // Настройка адаптеров
        createdRoomsAdapter = RoomAdapter(isHome = true, isCreatedRooms = true)
        joinedRoomsAdapter = RoomAdapter(isHome = true, isCreatedRooms = false)

        // Настройка LayoutManager и Adapter для созданных комнат
        createdRoomsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        createdRoomsRecyclerView.adapter = createdRoomsAdapter

        // Настройка LayoutManager и Adapter для присоединенных комнат
        joinedRoomsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        joinedRoomsRecyclerView.adapter = joinedRoomsAdapter

        //viewModel.listenToCreatedRooms()
        //viewModel.listenToJoinedRooms()

        // Обновление данных для созданных комнат
        viewModel.createdRooms.observe(viewLifecycleOwner) { rooms ->
            createdRoomsAdapter.updateRooms(rooms)
        }

        // Обновление данных для присоединенных комнат
        viewModel.joinedRooms.observe(viewLifecycleOwner) { rooms ->
            joinedRoomsAdapter.updateRooms(rooms)
        }

        // Получение данных
        viewModel.getCreatedRooms()
        viewModel.getJoinedRooms()

        createdRoomsAdapter.onCopyCodeClick = { code ->
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("Room Code", code))
            Toast.makeText(requireContext(), "Code copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        createdRoomsAdapter.onOpenRoomClick = { roomId, roomName, connectionCode ->
            val action = R.id.action_home_to_room
            val bundle = Bundle().apply {
                putString("roomId", roomId)          // Передача roomId
                putString("roomName", roomName)      // Передача имени комнаты
                putString("connectionCode", connectionCode) // Передача кода комнаты
                putBoolean("isOwner", true)
            }
            findNavController().navigate(action, bundle)
        }

        createdRoomsAdapter.onChangeCodeClick = { roomId ->
            val newCode = viewModel.generateConnectionCode()
            viewModel.updateRoomCode(roomId, newCode)
        }

        createdRoomsAdapter.onChangeRoomNameClick = { roomId, currentName ->
            val dialog = ChangeRoomNameDialog(roomId, currentName) { newName ->
                viewModel.updateRoomName(roomId, newName)
            }
            dialog.show(parentFragmentManager, "ChangeRoomNameDialog")
        }

        joinedRoomsAdapter.onCopyCodeClick = { code ->
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("Room Code", code))
            Toast.makeText(requireContext(), "Code copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        joinedRoomsAdapter.onOpenRoomClick = { roomId, roomName, connectionCode ->
            val action = R.id.action_home_to_room
            val bundle = Bundle().apply {
                putString("roomId", roomId)          // Передача roomId
                putString("roomName", roomName)      // Передача имени комнаты
                putString("connectionCode", connectionCode) // Передача кода комнаты
                putBoolean("isOwner", false)
            }
            findNavController().navigate(action, bundle)
        }


    }

}