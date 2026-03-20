package com.example.chatapp.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.auth.FirebaseAuth

object ChatRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun sendMessage(text: String) {

        val user = auth.currentUser

        val message = Message(
            senderId = user?.uid ?: "",
            senderEmail = user?.email ?: "Unknown", // 🔥 ADD
            text = text,
            timestamp = System.currentTimeMillis()
        )

        db.collection("messages").add(message)
    }

    fun listenMessages(onUpdate: (List<Message>) -> Unit) {
        db.collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, error ->

                if (error != null) return@addSnapshotListener

                val list = snapshot?.toObjects(Message::class.java) ?: emptyList()
                onUpdate(list)
            }
    }
}
