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
import com.example.courseworkapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SearchRoomDialog: DialogFragment() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

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
                    searchRoom(roomCode)
                }
            } else {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setView(view)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

    private fun searchRoom(roomCode: String) {
        val currentUserId = auth.currentUser?.uid ?: return

        db.collection("rooms")
            .whereEqualTo("connectionCode", roomCode)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Toast.makeText(requireContext(), "Комната с таким кодом не найдена", Toast.LENGTH_SHORT).show()
                } else {
                    val roomDocument = documents.first()
                    val roomId = roomDocument.id
                    val ownerId = roomDocument.getString("ownerId")
                    val participants = roomDocument.get("participants") as? List<String> ?: emptyList()

                    if (currentUserId == ownerId) {
                        Toast.makeText(requireContext(), "Вы являетесь создателем этой комнаты", Toast.LENGTH_SHORT).show()
                    }else if (currentUserId in participants) {
                        Toast.makeText(requireContext(), "Вы уже присоединились к этой комнате", Toast.LENGTH_SHORT).show()
                    } else {
                        val updatedParticipants = participants + currentUserId

                        db.collection("rooms").document(roomId)
                            .update("participants", updatedParticipants)
                            .addOnSuccessListener {
                                Toast.makeText(requireContext(), "Вы присоединились к комнате!", Toast.LENGTH_SHORT).show()
                                dismiss()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(requireContext(), "Ошибка при присоединении к комнате: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Ошибка при поиске комнаты: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


}