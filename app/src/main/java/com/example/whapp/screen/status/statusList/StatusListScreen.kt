package com.example.whapp.screen.status.statusList

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.whapp.components.BottomNavigationItem
import com.example.whapp.components.BottomNavigationMenu
import com.example.whapp.components.CommonDivider
import com.example.whapp.components.CommonRow
import com.example.whapp.components.TitleText
import com.example.whapp.navigation.AllScreens
import com.example.whapp.screen.ChatViewModel
import com.example.whapp.util.CommonProgressSpinner
import com.example.whapp.util.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StatusListScreen(navController: NavController, chatViewModel: ChatViewModel) {
    val inProgress = chatViewModel.inProgressStatus.value
    if (inProgress) {
        CommonProgressSpinner()
    } else {
        val statuses = chatViewModel.status.value
        val userData = chatViewModel.userData.value

        val myStatuses = statuses.filter { it.user.userId == userData?.userId }
        val otherStatuses = statuses.filter { it.user.userId != userData?.userId }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
        ) { uri ->
            uri?.let {
                chatViewModel.uploadStatus(imageUrl = uri)
            }
        }

        Scaffold(floatingActionButton = {
            FAB {
                launcher.launch("image/*")
            }
        }, content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                TitleText(txt = "Status")
                if (statuses.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "No statuses available")
                    }
                } else {
                    if (myStatuses.isNotEmpty()) {
                        CommonRow(
                            imageUrl = myStatuses[0].user.imageUrl,
                            name = myStatuses[0].user.name
                        ) {
                            navigateTo(
                                navController = navController,
                                AllScreens.SingleStatusScreen.name + "/${myStatuses[0].user.userId}"
                            )
                        }
                        CommonDivider()
                    }
                    val uniqueUsers = otherStatuses.map { it.user }.toSet().toList()

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(items = uniqueUsers) { user ->
                            CommonRow(imageUrl = user.imageUrl, name = user.name) {
                                navigateTo(
                                    navController = navController,
                                    AllScreens.SingleStatusScreen.name + "/${user.userId}"
                                )
                            }
                        }
                    }

                }
                BottomNavigationMenu(
                    selectedItem = BottomNavigationItem.STATUSLIST,
                    navController = navController
                )
            }
        })
    }
}


@Composable
fun FAB(onFabClick: () -> Unit) {
    FloatingActionButton(
        onClick = onFabClick,
        containerColor = MaterialTheme.colorScheme.secondary,
        shape = CircleShape,
        modifier = Modifier.padding(bottom = 40.dp)
    ) {
        Icon(imageVector = Icons.Rounded.Edit, contentDescription = null, tint = Color.White)
    }
}