package com.example.courseworkapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.courseworkapp.R
import com.example.courseworkapp.viewmodel.UserViewModel

class ParticipantAdapter(
    private val isOwner: Boolean,
    private val onDeleteClick: (String) -> Unit
) : RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder>() {

    private val participants = mutableListOf<String>()

    fun updateParticipants(newParticipants: List<String>) {
        participants.clear()
        participants.addAll(newParticipants)
        notifyDataSetChanged()
    }

    inner class ParticipantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.textViewUserName)
        val imageButtonDelete: ImageButton = view.findViewById(R.id.imageButtonDeleteUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_participant, parent, false)
        return ParticipantViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        val participantId = participants[position]
        val userViewModel = UserViewModel()

        userViewModel.getUserNameById(participantId) { userName ->
            holder.textViewName.text = userName
        }


        // Показываем кнопку удаления только для владельца
        if (isOwner) {
            holder.imageButtonDelete.visibility = View.VISIBLE
            holder.imageButtonDelete.setOnClickListener {
                onDeleteClick(participantId)
            }
        } else {
            holder.imageButtonDelete.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = participants.size
}
