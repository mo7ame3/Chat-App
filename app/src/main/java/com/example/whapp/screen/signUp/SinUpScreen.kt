package com.example.whapp.screen.signUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.whapp.R
import com.example.whapp.navigation.AllScreens
import com.example.whapp.screen.ChatViewModel
import com.example.whapp.util.CommonProgressSpinner
import com.example.whapp.util.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController, chatViewModel: ChatViewModel) {
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
            val nameState = remember { mutableStateOf(TextFieldValue()) }
            val numberState = remember { mutableStateOf(TextFieldValue()) }
            val emailState = remember { mutableStateOf(TextFieldValue()) }
            val passwordState = remember { mutableStateOf(TextFieldValue()) }
            val eye = remember { mutableStateOf(false) }
            val focus = LocalFocusManager.current


            Image(
                painter = painterResource(id = R.drawable.chat),
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .padding(top = 16.dp)
                    .padding(8.dp)
            )

            Text(
                text = "Signup",
                modifier = Modifier.padding(8.dp),
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif
            )

            OutlinedTextField(
                value = nameState.value,
                onValueChange = { nameState.value = it },
                modifier = Modifier.padding(8.dp),
                label = {
                    Text(
                        text = "Name"
                    )
                })
            OutlinedTextField(
                value = numberState.value,
                onValueChange = { numberState.value = it },
                modifier = Modifier.padding(8.dp),
                label = {
                    Text(
                        text = "Number"
                    )
                })
            OutlinedTextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                modifier = Modifier.padding(8.dp),
                label = {
                    Text(
                        text = "Email"
                    )
                })
            val visualTransformation = if (eye.value) VisualTransformation.None
            else PasswordVisualTransformation()
            OutlinedTextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                modifier = Modifier.padding(8.dp),
                label = {
                    Text(
                        text = "Password"
                    )
                }, visualTransformation = visualTransformation,
                trailingIcon = {
                    IconButton(onClick = { eye.value = !eye.value }) {
                        if (eye.value) Icon(
                            painter = painterResource(id = R.drawable.visibilityoff),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        else Icon(
                            painter = painterResource(id = R.drawable.visibility),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            )

            Button(
                onClick = {
                    focus.clearFocus(force = true)
                    chatViewModel.onSignup(
                        name = nameState.value.text,
                        number = numberState.value.text,
                        email = emailState.value.text,
                        password = passwordState.value.text,
                        navController = navController
                    )
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "SIGN UP")
            }


            Text(
                text = "Already a user? Go to login ->",
                color = Color.Blue,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navigateTo(navController, AllScreens.LoginScreen.name)
                    })

        }


        val isLoading = chatViewModel.inProgress.value

        if (isLoading) {
            CommonProgressSpinner()
        }

    }
}