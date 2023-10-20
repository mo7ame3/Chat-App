package com.example.whapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.whapp.screen.ChatViewModel
import com.example.whapp.screen.chat.chatList.ChatListScreen
import com.example.whapp.screen.chat.singleChat.SingleChatScreen
import com.example.whapp.screen.login.LoginScreen
import com.example.whapp.screen.profile.ProfileScreen
import com.example.whapp.screen.signUp.SignUpScreen
import com.example.whapp.screen.splash.SplashScreen
import com.example.whapp.screen.status.singleStatus.SingleStatusScreen
import com.example.whapp.screen.status.statusList.StatusListScreen
import com.example.whapp.util.NotificationMessage

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val chatViewModel = hiltViewModel<ChatViewModel>()


    NotificationMessage(chatViewModel = chatViewModel)
    NavHost(navController = navController, startDestination = AllScreens.SplashScreen.name) {
        composable(route = AllScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        composable(route = AllScreens.SingUpScreen.name) {
            SignUpScreen(navController = navController, chatViewModel = chatViewModel)
        }
        composable(route = AllScreens.LoginScreen.name) {
            LoginScreen(navController = navController, chatViewModel = chatViewModel)
        }
        composable(route = AllScreens.ProfileScreen.name) {
            ProfileScreen(navController = navController, chatViewModel = chatViewModel)
        }
        composable(route = AllScreens.ChatListScreen.name) {
            ChatListScreen(navController = navController, chatViewModel = chatViewModel)
        }
        composable(
            route = AllScreens.SingleChat.name + "/{chatId}",
            arguments = listOf(navArgument(name = "chatId") {
                type = NavType.StringType
            })
        ) { data ->
            SingleChatScreen(
                navController = navController,
                chatId = data.arguments?.getString("chatId").toString()
            )
        }
        composable(route = AllScreens.StatusListScreen.name) {
            StatusListScreen(navController = navController)
        }
        composable(
            route = AllScreens.SingleStatusScreen.name + "/{statusId}",
            arguments = listOf(navArgument(name = "statusId") {
                type = NavType.StringType
            })
        ) { data ->
            SingleStatusScreen(
                navController = navController,
                statusId = data.arguments?.getString("statusId").toString()
            )
        }
    }
}