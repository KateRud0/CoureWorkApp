package com.example.courseworkapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courseworkapp.data.Room
import com.example.courseworkapp.data.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class RoomViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _createdRooms = MutableLiveData<List<Room>>()
    val createdRooms: LiveData<List<Room>> get() = _createdRooms

    private val _joinedRooms = MutableLiveData<List<Room>>()
    val joinedRooms: LiveData<List<Room>> get() = _joinedRooms

    fun getCreatedRooms() {
        val userId = auth.currentUser?.uid ?: return
        db.collection("rooms")
            .whereEqualTo("ownerId", userId)
            .get()
            .addOnSuccessListener { documents ->
                val rooms = documents.map { it.toObject(Room::class.java) }
                _createdRooms.postValue(rooms)
            }
    }

    fun getJoinedRooms() {
        val userId = auth.currentUser?.uid ?: return
        db.collection("rooms")
            .whereArrayContains("participants", userId)
            .get()
            .addOnSuccessListener { documents ->
                val rooms = documents.map { it.toObject(Room::class.java) }
                _joinedRooms.postValue(rooms)
            }
    }

    fun updateRoomCode(roomId: String, newCode: String) {
        db.collection("rooms").document(roomId)
            .update("connectionCode", newCode)
            .addOnSuccessListener {
                Log.d("RoomViewModel", "Room code updated successfully")
            }
            .addOnFailureListener { e ->
                Log.e("RoomViewModel", "Error updating room code", e)
            }
    }

    fun updateRoomName(roomId: String, newName: String) {
        db.collection("rooms").document(roomId)
            .update("name", newName)
            .addOnSuccessListener {
                Log.d("RoomViewModel", "Room name updated successfully")
            }
            .addOnFailureListener { e ->
                Log.e("RoomViewModel", "Error updating room name", e)
            }
    }

    fun getRoomDetails(roomId: String, onComplete: (ownerId: String, participants: List<String>) -> Unit) {
        db.collection("rooms").document(roomId).get()
            .addOnSuccessListener { document ->
                val ownerId = document.getString("ownerId") ?: return@addOnSuccessListener
                val participants = document.get("participants") as? List<String> ?: emptyList()
                onComplete(ownerId, participants)
            }
            .addOnFailureListener { error ->
                Log.e("RoomViewModel", "Ошибка получения данных комнаты", error)
            }
    }

    fun removeParticipant(roomId: String, participantId: String) {
        val roomRef = db.collection("rooms").document(roomId)

        roomRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val participants = document.get("participants") as? List<String> ?: return@addOnSuccessListener

                    // Удаляем участника
                    val updatedParticipants = participants.filter { it != participantId }

                    roomRef.update("participants", updatedParticipants)
                        .addOnSuccessListener {
                            Log.d("RoomViewModel", "Participant removed successfully")
                        }
                        .addOnFailureListener { e ->
                            Log.e("RoomViewModel", "Failed to remove participant", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.e("RoomViewModel", "Error fetching room", e)
            }
    }


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
        val currentUserId =
            auth.currentUser?.uid ?: return onFailure("Пользователь не авторизован")

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

    fun saveTask(roomId: String, task: Task) {
        val taskRef = db.collection("rooms").document(roomId).collection("tasks").document(task.id)
        taskRef.set(task)
            .addOnSuccessListener {
                Log.d("RoomViewModel", "Task saved successfully")
            }
            .addOnFailureListener { e ->
                Log.e("RoomViewModel", "Failed to save task", e)
            }
    }

    fun getTaskDetails(taskId: String): LiveData<Task> {
        val taskLiveData = MutableLiveData<Task>()
        db.collectionGroup("tasks").whereEqualTo("id", taskId).get()
            .addOnSuccessListener { snapshot ->
                snapshot.documents.firstOrNull()?.toObject(Task::class.java)?.let {
                    taskLiveData.postValue(it)
                }
            }
            .addOnFailureListener { e ->
                Log.e("RoomViewModel", "Failed to fetch task details", e)
            }
        return taskLiveData
    }

    fun getTasks(roomId: String): LiveData<List<Task>> {
        val tasksLiveData = MutableLiveData<List<Task>>()

        db.collection("rooms").document(roomId).collection("tasks")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val tasks = querySnapshot.documents.mapNotNull { it.toObject(Task::class.java) }
                tasksLiveData.postValue(tasks)
            }
            .addOnFailureListener { e ->
                Log.e("RoomViewModel", "Ошибка получения заданий: ", e)
            }

        return tasksLiveData
    }

    fun deleteTask(taskId: String, roomId: String) {
        db.collection("rooms").document(roomId).collection("tasks").document(taskId)
            .delete()
            .addOnSuccessListener {
                // Логика после успешного удаления
                Log.d("RoomViewModel", "Task deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.e("RoomViewModel", "Error deleting task", e)
            }
    }





    fun generateConnectionCode(): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return "#" + (1..6).map { charset.random() }.joinToString("")
    }

}