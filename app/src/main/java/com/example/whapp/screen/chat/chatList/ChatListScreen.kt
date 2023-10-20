package com.example.whapp.screen.chat.chatList

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.whapp.components.BottomNavigationItem
import com.example.whapp.components.BottomNavigationMenu
import com.example.whapp.components.CommonRow
import com.example.whapp.navigation.AllScreens
import com.example.whapp.screen.ChatViewModel
import com.example.whapp.util.CommonProgressSpinner
import com.example.whapp.util.navigateTo

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(navController: NavController, chatViewModel: ChatViewModel) {
    val inProgress = chatViewModel.inProgressChat.value
    if (inProgress) {
        CommonProgressSpinner()
    } else {
        val chats = chatViewModel.chats.value
        val userData = chatViewModel.userData.value

        val showDialog = remember { mutableStateOf(false) }

        Scaffold(floatingActionButton = {
            FAB(showDialog = showDialog.value, onFabClick = {
                showDialog.value = true
            }, onDismiss = {
                showDialog.value = false
            }, onAddChat = {
                chatViewModel.onAddChat(it)
                showDialog.value = false
            })
        }, content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                if (chats.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "No chats available")
                    }
                } else {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(chats) { chat ->

                            val chatUser =
                                if (chat.sender.userId == userData?.userId) chat.receiver else chat.sender

                            CommonRow(
                                imageUrl = chatUser.imageUrl ?: "", name = chatUser.name
                            ) {
                                chat.chatId?.let { id ->
                                    navigateTo(navController, AllScreens.SingleChat.name + "/$id")
                                }
                            }

                        }
                    }
                }
                BottomNavigationMenu(
                    selectedItem = BottomNavigationItem.CHATLIIST, navController = navController
                )
            }
        })

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAB(
    showDialog: Boolean,
    onFabClick: () -> Unit,
    onDismiss: () -> Unit,
    onAddChat: (String) -> Unit,
) {
    val addChatNumber = remember { mutableStateOf("") }
    if (showDialog) AlertDialog(onDismissRequest = onDismiss, confirmButton = {
        Button(onClick = {
            onAddChat(addChatNumber.value)
            addChatNumber.value = ""
        }) {
            Text(text = "Add chat")
        }
    }, title = { Text(text = "Add chat") }, text = {
        OutlinedTextField(
            value = addChatNumber.value,
            onValueChange = { addChatNumber.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    })
    FloatingActionButton(
        onClick = onFabClick,
        containerColor = MaterialTheme.colorScheme.secondary,
        shape = CircleShape,
        modifier = Modifier.padding(bottom = 40.dp)
    ) {
        Icon(imageVector = Icons.Rounded.Add, contentDescription = null, tint = Color.White)
    }
}