package com.example.whapp.screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.whapp.data.COLLECTION_USER
import com.example.whapp.data.Event
import com.example.whapp.data.UserData
import com.example.whapp.navigation.AllScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage
) : ViewModel() {
    val inProgress = mutableStateOf(false)
    val popupNotification = mutableStateOf<Event<String>?>(null)
    private val signedIn = mutableStateOf(false)
    private val userData = mutableStateOf<UserData?>(null)


    init {
        val currentUser = auth.currentUser
        signedIn.value = currentUser != null
        currentUser?.uid?.let {
            getUserData(it)
        }
    }

    fun onSignup(
        name: String,
        number: String,
        email: String,
        password: String,
        navController: NavController
    ) {
        if (name.isEmpty() || number.isEmpty() || email.isEmpty() || password.isEmpty()) {
            handleException(customMessage = "Please fill in all fields")
            return
        }
        inProgress.value = true
        db.collection(COLLECTION_USER).whereEqualTo("number", number).get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                signedIn.value = true
                                createOrUpdateProfile(name = name, number = number)
                                navController.navigate(route = AllScreens.ProfileScreen.name) {
                                    navController.popBackStack()
                                    navController.popBackStack()
                                    navController.popBackStack()
                                }
                            } else {
                                handleException(customMessage = "Signup Failed")
                            }
                        }
                        .addOnFailureListener { exception ->
                            handleException(exception)
                        }
                } else {
                    handleException(customMessage = "number already exists")
                }
                inProgress.value = false
            }
    }


    fun onLogin(email: String, password: String, navController: NavController) {
        inProgress.value = true
        if (email.isEmpty() || password.isEmpty()) {
            inProgress.value = false
            handleException(customMessage = "please fill in all fields")
            return
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        signedIn.value = true
                        inProgress.value = false
                        auth.currentUser?.uid?.let {
                            getUserData(uid = it)
                        }
                        navController.navigate(route = AllScreens.ProfileScreen.name) {
                            navController.popBackStack()
                            navController.popBackStack()
                            navController.popBackStack()
                        }
                    } else {
                        handleException(exception = task.exception, customMessage = "Login failed")
                    }
                }
                .addOnFailureListener { exception ->
                    handleException(exception = exception, customMessage = "Login failed")
                }
        }
    }

    fun onLogout() {
        auth.signOut()
        signedIn.value = false
        userData.value = null
        popupNotification.value = Event("Logged out")
    }


    private fun createOrUpdateProfile(
        name: String? = null,
        number: String? = null,
        imageUrl: String? = null
    ) {
        val uid = auth.currentUser?.uid

        val userDataInCreate = UserData(
            userId = uid,
            name = name ?: userData.value?.name,
            number = number ?: userData.value?.number,
            imageUrl = imageUrl ?: userData.value?.imageUrl
        )

        uid?.let { letUid ->
            inProgress.value = true
            db.collection(COLLECTION_USER).document(letUid)
                .get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        it.reference.update(userDataInCreate.toMap())
                            .addOnSuccessListener {
                                inProgress.value = false
                            }
                            .addOnFailureListener { exception ->
                                handleException(exception, "can't update user")
                            }
                    } else {
                        db.collection(COLLECTION_USER).document(letUid).set(userDataInCreate)
                        inProgress.value = false
                        getUserData(letUid)
                    }
                }
                .addOnFailureListener { exception ->
                    handleException(exception, "can't retrieve user")
                }

        }

    }


    private fun getUserData(uid: String) {
        inProgress.value = true
        db.collection(COLLECTION_USER).document(uid)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    handleException(error, "can't retrieve user data")
                }
                if (value != null) {
                    val user = value.toObject<UserData>()
                    userData.value = user
                    inProgress.value = false
                }
            }
    }

    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.e("TAG", "Chat app exception", exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMsg else "$customMessage: $errorMsg"

        popupNotification.value = Event(message)
        inProgress.value = false
    }


}