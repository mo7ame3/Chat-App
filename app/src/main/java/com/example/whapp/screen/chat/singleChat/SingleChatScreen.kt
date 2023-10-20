package com.example.whapp.screen.chat.singleChat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.whapp.components.CommonDivider
import com.example.whapp.components.CommonImage
import com.example.whapp.data.Message
import com.example.whapp.screen.ChatViewModel

@Composable
fun SingleChatScreen(chatId: String, navController: NavController, chatViewModel: ChatViewModel) {

//    LaunchedEffect(key1 = Unit) {
//
//    }

    val currentChat = chatViewModel.chats.value.first { it.chatId == chatId }
    val myUser = chatViewModel.userData.value
    val chatUser = if (myUser?.userId == currentChat.sender.userId) currentChat.receiver
    else currentChat.sender

    var reply by rememberSaveable { mutableStateOf("") }
    val chatMessages = chatViewModel.chatMessages
    Column(modifier = Modifier.fillMaxSize()) {
        ChatHeader(name = chatUser.name ?: "", imageUrl = chatUser.imageUrl ?: "") {
            navController.popBackStack()
        }

        Messages(
            modifier = Modifier.weight(1f),
            chatMessages = chatMessages.value,
            currentUserId = myUser?.userId ?: ""
        )

        ReplyBox(reply = reply, onReplyChange = { reply = it }) {
            chatViewModel.onSendReply(chatId = chatId, message = reply)
            reply = ""
        }

    }
}


@Composable
fun ChatHeader(name: String, imageUrl: String, onBackClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Rounded.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .clickable { onBackClicked.invoke() }
                .padding(8.dp))

        CommonImage(
            imageUrl = imageUrl, modifier = Modifier
                .padding(8.dp)
                .size(50.dp)
                .clip(CircleShape)
        )

        Text(text = name, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 4.dp))

    }
}


@Composable
fun Messages(modifier: Modifier, chatMessages: List<Message>, currentUserId: String) {
    LazyColumn(modifier = modifier) {
        items(chatMessages) { msg ->
            val alignment = if (msg.sentBy == currentUserId) Alignment.End
            else Alignment.Start
            val color = if (msg.sentBy == currentUserId) Color(0xFF68C400)
            else Color(0xFFC0C0C0)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalAlignment = alignment
            ) {
                Text(
                    text = msg.message ?: "",
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(color)
                        .padding(12.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyBox(reply: String, onReplyChange: (String) -> Unit, onSendReply: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CommonDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = reply,
                onValueChange = onReplyChange,
                maxLines = 3,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.LightGray.copy(alpha = 0.2f)
                )
            )

            Button(onClick = onSendReply) {
                Text(text = "Send")
            }
        }
    }
}