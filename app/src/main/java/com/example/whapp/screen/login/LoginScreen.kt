package com.example.whapp.screen.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.example.whapp.components.ChatButton
import com.example.whapp.components.ChatLabel
import com.example.whapp.components.ChatLogo
import com.example.whapp.components.NavText
import com.example.whapp.components.PasswordInput
import com.example.whapp.components.TextInput
import com.example.whapp.navigation.AllScreens
import com.example.whapp.screen.ChatViewModel
import com.example.whapp.util.CommonProgressSpinner
import com.example.whapp.util.navigateTo

@Composable
fun LoginScreen(navController: NavController, chatViewModel: ChatViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val emailState = remember { mutableStateOf(TextFieldValue()) }
            val passwordState = remember { mutableStateOf(TextFieldValue()) }
            val eye = remember { mutableStateOf(false) }
            val focus = LocalFocusManager.current


            ChatLogo()

            ChatLabel(label = "Login")

            TextInput(value = emailState, label = "Email")

           PasswordInput(value = passwordState, eye = eye)

           ChatButton(label = "LOGIN") {
               focus.clearFocus(force = true)
               chatViewModel.onLogin(
                   email = emailState.value.text,
                   password = passwordState.value.text,
                   navController = navController
               )
           }


          NavText(label = "New here? Go to signup ->") {
              navigateTo(navController, AllScreens.SingUpScreen.name)
          }

        }
        val isLoading = chatViewModel.inProgress.value

        if (isLoading) {
            CommonProgressSpinner()
        }

    }
}