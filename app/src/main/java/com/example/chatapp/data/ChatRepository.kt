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
            senderEmail = user?.email ?: "Unknown",
            text = text,
            timestamp = System.currentTimeMillis()
        )

        db.collection("messages").add(message)
    }

    fun listenMessages(onUpdate: (List<Message>) -> Unit) {

        val userId = auth.currentUser?.uid ?: return

        val userRef = db.collection("users").document(userId)

        userRef.get().addOnSuccessListener { userDoc ->

            val hidden = userDoc.get("hiddenMessages") as? List<String> ?: emptyList()

            db.collection("messages")
                .orderBy("timestamp")
                .addSnapshotListener { snapshot, error ->

                    if (error != null) return@addSnapshotListener

                    val list = snapshot?.documents?.map {
                        Message(
                            senderId = it["senderId"] as String,
                            senderEmail = it["senderEmail"] as String,
                            text = it["text"] as String,
                            timestamp = it["timestamp"] as Long
                        )
                    }?.filterIndexed { index, _ ->
                        val docId = snapshot.documents[index].id
                        !hidden.contains(docId)
                    } ?: emptyList()

                    onUpdate(list)
                }
        }
    }
    fun clearAllMessages(onComplete: (Boolean) -> Unit = {}) {

        db.collection("messages")
            .get()
            .addOnSuccessListener { snapshot ->

                val batch = db.batch()

                for (doc in snapshot.documents) {
                    batch.delete(doc.reference)
                }

                batch.commit()
                    .addOnSuccessListener {
                        onComplete(true)
                    }
                    .addOnFailureListener {
                        onComplete(false)
                    }
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }
    fun deleteMyMessages(onComplete: (Boolean) -> Unit = {}) {

        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("messages")
            .whereEqualTo("senderId", currentUserId)
            .get()
            .addOnSuccessListener { snapshot ->

                val batch = db.batch()

                for (doc in snapshot.documents) {
                    batch.delete(doc.reference)
                }

                batch.commit()
                    .addOnSuccessListener {
                        onComplete(true)
                    }
                    .addOnFailureListener {
                        onComplete(false)
                    }
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }
    fun clearChatForCurrentUser(messages: List<Message>) {

        val userId = auth.currentUser?.uid ?: return

        db.collection("messages")
            .get()
            .addOnSuccessListener { snapshot ->

                val allIds = snapshot.documents.map { it.id }

                db.collection("users")
                    .document(userId)
                    .set(
                        mapOf("hiddenMessages" to allIds),
                        com.google.firebase.firestore.SetOptions.merge()
                    )
            }
    }
}