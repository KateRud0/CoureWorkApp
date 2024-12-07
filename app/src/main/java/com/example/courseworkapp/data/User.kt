package com.example.courseworkapp.data

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val createdRooms: List<String> = emptyList(),
    val joinedRooms: List<String> = emptyList()
)
