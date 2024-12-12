package com.example.courseworkapp.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.courseworkapp.R
import com.example.courseworkapp.viewmodel.RoomViewModel


class SearchRoomDialog: DialogFragment() {

    private val roomViewModel: RoomViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_search_room, null)

        val connectionCodeEditText = view.findViewById<EditText>(R.id.editTextRoomCode)
        val searchRoomButton = view.findViewById<Button>(R.id.buttonSearchRoom)
        val closeButton = view.findViewById<ImageButton>(R.id.imageButtonСloseSR)

        connectionCodeEditText.filters = arrayOf(InputFilter.AllCaps())

        closeButton.setOnClickListener {
            dismiss()
        }

        searchRoomButton.setOnClickListener {
            val roomCode = connectionCodeEditText.text.toString().trim()

            if (roomCode.isNotEmpty()) {
                if ((roomCode.length != 7) || !("#" in roomCode)) {
                    Toast.makeText(requireContext(), "Код введен не правильно", Toast.LENGTH_SHORT).show()
                } else{
                    roomViewModel.searchRoom(
                        roomCode = roomCode,
                        onSuccess = { room ->
                            Toast.makeText(requireContext(), "Вы присоединились к комнате: ${room.name}", Toast.LENGTH_SHORT).show()
                            dismiss()
                        },
                        onFailure = { error ->
                            Toast.makeText(requireContext(), "Ошибка: $error", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            } else {
                Toast.makeText(requireContext(), "Введите код комнаты", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setView(view)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

}