@file:Suppress("UNUSED_EXPRESSION")

package com.example.whapp.util

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.whapp.screen.ChatViewModel

fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route = route) {
        popUpTo(route)
        launchSingleTop = true
    }
}


@Composable
fun CommonProgressSpinner() {
    Row(
        modifier = Modifier
            .apply { 0.5f }
            .background(Color.LightGray)
            .clickable(enabled = false) {}
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator()
    }
}


@Composable
fun NotificationMessage(chatViewModel: ChatViewModel) {
    val notifState = chatViewModel.popupNotification.value
    val notifMessage = notifState?.getContentOrNull()
    if (!notifMessage.isNullOrEmpty())
        Toast.makeText(LocalContext.current, notifMessage, Toast.LENGTH_SHORT).show()
}

//@SuppressLint("RememberReturnType")
//@Composable
//fun CheckSignedIn(chatViewModel: ChatViewModel , navController: NavController){
//    val alreadySignedIn = remember { mutableStateOf(false) }
//    val signedIn = chatViewModel.signedIn.value
//    if(signedIn && !alreadySignedIn.value){
//        alreadySignedIn.value = true
//        navigateTo(navController = navController , route = AllScreens.ProfileScreen.name)
//
//    }
//
//
//}