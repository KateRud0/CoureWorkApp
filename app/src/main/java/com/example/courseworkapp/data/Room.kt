package com.example.courseworkapp.data

data class Room(
    val id: String = "",
    val name: String = "",
    val ownerId: String = "",
    val connectionCode: String = "",
    val participants: List<String> = emptyList()
)
