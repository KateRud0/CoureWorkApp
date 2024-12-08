package com.example.courseworkapp.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.courseworkapp.R
import com.example.courseworkapp.viewmodel.RoomViewModel

class AddRoomDialog : DialogFragment() {

    private var connectionCode: String = ""
    private val roomViewModel: RoomViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = android.app.AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_room, null)

        val roomNameEditText = view.findViewById<EditText>(R.id.editTextRoomName)
        val connectionCodeTextView = view.findViewById<TextView>(R.id.textViewRoomCode)
        val regenerateCodeButton = view.findViewById<ImageButton>(R.id.buttonChangeCode)
        val createRoomButton = view.findViewById<Button>(R.id.buttonCreateRoom)
        val closeButton = view.findViewById<ImageButton>(R.id.imageButtonСloseCR)


        connectionCode =  roomViewModel.generateConnectionCode()
        connectionCodeTextView.text = "$connectionCode"

        regenerateCodeButton.setOnClickListener {
            connectionCode = roomViewModel.generateConnectionCode()
            connectionCodeTextView.text = "$connectionCode"
        }

        closeButton.setOnClickListener {
            dismiss()
        }

        createRoomButton.setOnClickListener {
            val roomName = roomNameEditText.text.toString().trim()

            if (roomName.isNotEmpty()) {
                roomViewModel.createRoom(
                    name = roomName,
                    roomCode = connectionCode,
                    onSuccess = {
                        Toast.makeText(requireContext(), "Комната успешно создана!", Toast.LENGTH_SHORT).show()
                        dismiss()
                    },
                    onFailure = { error ->
                        Toast.makeText(requireContext(), "Ошибка: $error", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setView(view)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent) // Установка прозрачного фона
        return dialog
    }
}