package com.sonder.peeker.presentation.notification_list

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.MarkEmailRead
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.domain.model.Notification
import com.sonder.peeker.presentation.Screen
import com.sonder.peeker.presentation.authentication.AuthViewModel
import com.sonder.peeker.presentation.ui.theme.Gray
import com.sonder.peeker.presentation.ui.theme.Pink
import com.sonder.peeker.presentation.ui.theme.White

@Composable
fun NotificationListScreen(
    navController: NavController,
    viewModel: NotificationListViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column() {
            NotificationTitle(navController)
            if (!viewModel.state.value.isLoading)
                NotificationPreview(navController, viewModel)
            else Box(
                modifier = Modifier
                    .padding(15.dp)
                    .width(30.dp)
                    .height(30.dp)
            ) { CircularProgressIndicator() }
        }
    }
}

@Composable
fun NotificationTitle(
    navController: NavController,
    viewModel: NotificationListViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),

        ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    navController.navigate(Screen.HomeScreen.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Icon",
                    tint = MaterialTheme.colors.primary
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = authViewModel.getUserName(),
                style = MaterialTheme.typography.body1,
                color = Pink
            )
            Text(text = "Notificaciones", style = MaterialTheme.typography.h2)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotificationPreview(
    navController: NavController,
    viewModel: NotificationListViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 32.dp)
    ) {
        if (!viewModel.state.value.error.isNullOrEmpty())
            Text(
                viewModel.state.value.error,
                style = MaterialTheme.typography.body1,
                color = Pink,
                modifier = Modifier.padding(15.dp)
            )
        else if (viewModel.notifications().isNullOrEmpty()) {
            Text(
                "No tienes notificaciones",
                style = MaterialTheme.typography.body1,
                color = Pink,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 64.dp)
            )

        } else {
            Box(
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                )
                {

                    items(viewModel.notifications().size) {
                        var notification: Notification? =
                            getNotificationAtIndex(viewModel.notifications(), it)
                        if (notification != null) NotificationItem(
                            navController,
                            notification
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItem(
    navController: NavController,
    notification: Notification,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(Gray),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    notification.heading,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    color = White,
                )
                if (!notification.subtitle.isNullOrEmpty()) Text(
                    notification.subtitle,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Pink.copy(alpha = 0.8f)
                )
            }
            Text(
                notification.content,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                color = White,
            )
        }
    }
}

fun getNotificationAtIndex(notifications: List<Notification>, index: Int): Notification? {
    try {
        return notifications[index]
    } catch (ex: Exception) {
        Log.d("Notification At Index", ex.toString())
        return null
    }
}