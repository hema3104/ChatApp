package com.example.chatapp.data

data class Message(
    val id: String = "",
    val senderId: String = "",
    val senderEmail: String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis()
)