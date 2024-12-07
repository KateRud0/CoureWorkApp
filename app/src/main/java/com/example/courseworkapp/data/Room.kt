package com.example.courseworkapp.data

data class Room(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val ownerId: String = "",
    val participants: List<String> = emptyList()
)
