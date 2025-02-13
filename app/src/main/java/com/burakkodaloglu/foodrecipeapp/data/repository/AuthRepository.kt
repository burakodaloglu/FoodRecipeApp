package com.burakkodaloglu.foodrecipeapp.data.repository

import com.burakkodaloglu.foodrecipeapp.presentation.common.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    suspend fun signUp(email: String, password: String): Resource<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!.uid)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage.orEmpty())
        }
    }

    suspend fun signIn(email: String, password: String): Resource<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user?.uid.orEmpty())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage.orEmpty())
        }
    }

    suspend fun saveFullNameToFirebase(userId: String, name: String, lastName: String): Resource<Unit> {
        return try {
            val userMap = hashMapOf(
                "name" to name,
                "lastName" to lastName
            )
            firestore.collection("users").document(userId).set(userMap).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage.orEmpty())
        }
    }


    suspend fun getFullNameFromFirebase(userId: String): Resource<String> {
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            val name = document.getString("name") ?: "User"
            val lastName = document.getString("lastName") ?: ""
            val fullName = "$name $lastName".trim()
            Resource.Success(fullName)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage.orEmpty())
        }
    }


    suspend fun signOut() = auth.signOut()
}