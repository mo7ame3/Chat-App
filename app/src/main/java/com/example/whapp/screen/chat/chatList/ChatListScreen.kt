package com.example.whapp.screen.chat.chatList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.whapp.components.BottomNavigationItem
import com.example.whapp.components.BottomNavigationMenu

@Composable
fun ChatListScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Chat List")
        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.CHATLIIST,
            navController = navController
        )
    }
}