package com.example.courseworkapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.courseworkapp.data.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class RoomViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun createRoom(
        name: String,
        roomCode: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val ownerId = auth.currentUser?.uid ?: return onFailure("Пользователь не авторизован")

        val roomId = UUID.randomUUID().toString()

        val room = Room(
            id = roomId,
            name = name,
            ownerId = ownerId,
            connectionCode = roomCode,
            participants = emptyList()
        )

        db.collection("rooms").document(roomId)
            .set(room)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Unknown error")
            }
    }

    // Функция поиска комнаты
    fun searchRoom(
        roomCode: String,
        onSuccess: (Room) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val currentUserId = auth.currentUser?.uid ?: return onFailure("Пользователь не авторизован")

        db.collection("rooms")
            .whereEqualTo("connectionCode", roomCode)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    onFailure("Комната не найдена")
                } else {
                    val roomDocument = documents.first()
                    val room = roomDocument.toObject(Room::class.java)

                    if (room.ownerId == currentUserId) {
                        onFailure("Вы являетесь создателем этой комнаты")
                    } else if (currentUserId in room.participants) {
                        onFailure("Вы уже присоединились к этой комнате")
                    } else {
                        val updatedParticipants = room.participants + currentUserId
                        db.collection("rooms").document(room.id)
                            .update("participants", updatedParticipants)
                            .addOnSuccessListener {
                                onSuccess(room)
                            }
                            .addOnFailureListener { e ->
                                onFailure(e.message ?: "Ошибка обновления пользователя")
                            }
                    }
                }
            }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Ошибка при присоединении к комнате")
            }
    }
        fun generateConnectionCode(): String {
            val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
            return "#" + (1..6).map { charset.random() }.joinToString("")
        }
    }