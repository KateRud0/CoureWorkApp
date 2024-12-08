package com.example.courseworkapp.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.courseworkapp.R
import com.example.courseworkapp.data.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
class AddRoomDialog : DialogFragment() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var connectionCode: String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = android.app.AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_room, null)

        val roomNameEditText = view.findViewById<EditText>(R.id.editTextRoomName)
        val connectionCodeTextView = view.findViewById<TextView>(R.id.textViewRoomCode)
        val regenerateCodeButton = view.findViewById<ImageButton>(R.id.buttonChangeCode)
        val createRoomButton = view.findViewById<Button>(R.id.buttonCreateRoom)
        val closeButton = view.findViewById<ImageButton>(R.id.imageButtonСloseCR)


        connectionCode = generateConnectionCode()
        connectionCodeTextView.text = "$connectionCode"

        regenerateCodeButton.setOnClickListener {
            connectionCode = generateConnectionCode()
            connectionCodeTextView.text = "$connectionCode"
        }

        closeButton.setOnClickListener {
            dismiss()
        }

        createRoomButton.setOnClickListener {
            val roomName = roomNameEditText.text.toString().trim()

            if (roomName.isNotEmpty()) {
                createRoom(roomName)
            } else {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setView(view)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent) // Установка прозрачного фона
        return dialog
    }

    private fun generateConnectionCode(): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        var code = (1..6)
            .map { charset.random() }
            .joinToString("")
        return "#$code"
    }

    private fun createRoom(name: String) {
        val ownerId = auth.currentUser?.uid
        if (ownerId != null) {
            val roomId = UUID.randomUUID().toString()

            val room = Room(
                roomId,
                name,
                ownerId,
                connectionCode,
                emptyList<String>()
            )

            db.collection("rooms").document(roomId)
                .set(room)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Комната успешно создана!", Toast.LENGTH_SHORT).show()
                    dismiss() // Закрываем диалог
                }
                .addOnFailureListener { e ->
                    Log.e("FirestoreError", "Error creating room", e)
                    Toast.makeText(requireContext(), "Ошибка создания комнаты", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(requireContext(), "Пользователь не авторизован", Toast.LENGTH_SHORT).show()
        }
    }
}