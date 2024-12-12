package com.example.courseworkapp.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.courseworkapp.R

class ChangeRoomNameDialog(
    private val roomId: String,
    private val currentName: String,
    private val onNameChanged: (String) -> Unit
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = android.app.AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_change_room_name, null)

        val closeButton = view.findViewById<ImageButton>(R.id.imageButtonСloseCRM)
        val editTextNewRoomName = view.findViewById<EditText>(R.id.editTextNewRoomName)
        val buttonEditName = view.findViewById<Button>(R.id.buttonEditName)

        // Устанавливаем текущее имя в поле ввода
        editTextNewRoomName.setText(currentName)

        closeButton.setOnClickListener {
            dismiss()
        }

        buttonEditName.setOnClickListener {
            val newRoomName = editTextNewRoomName.text.toString().trim()

            if (newRoomName.isNotEmpty() && newRoomName != currentName) {
                onNameChanged(newRoomName) // Передаем новое имя через callback
                dismiss()
            } else {
                Toast.makeText(context, "Введите другое имя", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setView(view)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent) // Установка прозрачного фона
        return dialog
    }
}
