package com.example.whapp.screen.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.whapp.components.BottomNavigationItem
import com.example.whapp.components.BottomNavigationMenu
import com.example.whapp.components.CommonDivider
import com.example.whapp.components.CommonImage
import com.example.whapp.components.ProfileChangeDetails
import com.example.whapp.navigation.AllScreens
import com.example.whapp.screen.ChatViewModel
import com.example.whapp.util.CommonProgressSpinner
import com.example.whapp.util.navigateTo

@Composable
fun ProfileScreen(navController: NavController, chatViewModel: ChatViewModel) {
    val inProgress = chatViewModel.inProgress.value
    if (inProgress) {
        CommonProgressSpinner()
    } else {
        val userData = chatViewModel.userData.value
        var name by rememberSaveable { mutableStateOf(userData?.name ?: "") }
        var number by rememberSaveable { mutableStateOf(userData?.number ?: "") }

        val scrollState = rememberScrollState()
        val focus = LocalFocusManager.current

        Column {
            ProfileContent(modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(8.dp),
                chatViewModel = chatViewModel,
                name = name,
                number = number,
                onNameChange = { name = it },
                onNumberChange = { number = it },
                onSave = {
                    focus.clearFocus(force = true)
                    chatViewModel.updateProfileData(name = name, number = number)
                },
                onBack = {
                    focus.clearFocus(force = true)
                    navigateTo(navController, AllScreens.ChatListScreen.name)
                },
                onLogout = {
                    chatViewModel.onLogout()
                    navigateTo(navController, AllScreens.LoginScreen.name)
                })

            BottomNavigationMenu(
                selectedItem = BottomNavigationItem.PROFILE, navController = navController
            )
        }
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier,
    chatViewModel: ChatViewModel,
    name: String,
    number: String,
    onNameChange: (String) -> Unit,
    onNumberChange: (String) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit,
) {
    val imageUrl = chatViewModel.userData.value?.imageUrl
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Back", modifier = Modifier.clickable { onBack.invoke() })
            Text(text = "Save", modifier = Modifier.clickable { onSave.invoke() })
        }

        CommonDivider()

        ProfileImage(imageUrl = imageUrl, chatViewModel = chatViewModel)

        CommonDivider()

        ProfileChangeDetails(label = "Name", value = name, onValueChange = onNameChange)

        ProfileChangeDetails(label = "Number", value = number, onValueChange = onNumberChange)

        CommonDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Logout", modifier = Modifier.clickable { onLogout.invoke() })
        }

    }
}

@Composable
fun ProfileImage(imageUrl: String?, chatViewModel: ChatViewModel) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        uri?.let {
            chatViewModel.uploadProfileImage(uri)
        }
    }

    Box(modifier = Modifier.height(IntrinsicSize.Min)) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    launcher.launch("image/*")
                }, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape, modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            ) {
                CommonImage(
                    imageUrl = imageUrl,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }

            Text(text = "Change Profile Picture")

        }

        val isLoading = chatViewModel.inProgress.value
        if (isLoading) {
            CommonProgressSpinner()
        }

    }
}
