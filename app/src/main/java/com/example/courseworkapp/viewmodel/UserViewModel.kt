package com.example.courseworkapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class UserViewModel : ViewModel() {

    private val _userName = MutableLiveData<String>()
    private val db = FirebaseFirestore.getInstance()
    val userName: LiveData<String> get() = _userName

    fun setUserName(name: String) {
        _userName.value = name
    }

    fun getUserNameById(userId: String, onComplete: (String) -> Unit) {

        db.collection("users").document(userId).get()
            .addOnSuccessListener { userDoc ->
                val userName = userDoc.getString("name") ?: "Unknown"
                onComplete(userName)
            }
            .addOnFailureListener { error ->
                Log.e("UserViewModel", "Ошибка получения имени пользователя", error)
                onComplete("Unknown")
            }

    }
}