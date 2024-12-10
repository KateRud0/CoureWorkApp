package com.example.courseworkapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseworkapp.R
import com.example.courseworkapp.viewmodel.RoomViewModel
import com.example.courseworkapp.viewmodel.UserViewModel


class ParticipantsFragment : Fragment(R.layout.fragment_participants) {
    private lateinit var adapter: ParticipantAdapter
    private lateinit var roomViewModel: RoomViewModel
    private var userViewModel = UserViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewParticipants)
        val textViewCreatorName = view.findViewById<TextView>(R.id.textViewCreatorName)

        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)

        val isOwner = arguments?.getBoolean("isOwner") ?: false
        // Получение данных о комнате
        val roomId = arguments?.getString("roomId") ?: return

        adapter = ParticipantAdapter(isOwner) { participantId ->
            roomViewModel.removeParticipant(roomId, participantId) // Удаляем участника
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter



        // Получаем данные комнаты
        roomViewModel.getRoomDetails(roomId) { ownerId, participants ->
            // Получаем имя создателя
            userViewModel.getUserNameById(ownerId) { ownerName ->
                textViewCreatorName.text = ownerName
            }

            // Обновляем список участников
            adapter.updateParticipants(participants)
        }
    }
}