package com.example.whapp.util

import androidx.navigation.NavController

fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route = route) {
        popUpTo(route)
        launchSingleTop = true
    }
}