package com.example.chatapp.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseRepository {

    private val auth = FirebaseAuth.getInstance()

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true)
                } else {
                    onResult(false)
                }
            }
    }
    fun saveUserToFirestore() {

        val user = FirebaseAuth.getInstance().currentUser ?: return

        val userMap = hashMapOf(
            "uid" to user.uid,
            "email" to user.email
        )

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(user.uid)
            .set(userMap)
    }

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
    }
}