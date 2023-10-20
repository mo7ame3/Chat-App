package com.example.whapp.screen.status.statusList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.whapp.components.BottomNavigationItem
import com.example.whapp.components.BottomNavigationMenu

@Composable
fun StatusListScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "StatusList")
        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.STATUSLIST,
            navController = navController
        )
    }
}