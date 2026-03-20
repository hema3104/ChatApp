package com.example.chatapp.data

data class Message(
    val senderId: String = "",
    val senderEmail: String = "",   // 🔥 ADD THIS
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis()
)