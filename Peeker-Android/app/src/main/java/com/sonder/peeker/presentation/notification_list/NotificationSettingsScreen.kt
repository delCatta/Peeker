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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.MarkEmailRead
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.rounded.*
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
import com.sonder.peeker.presentation.ui.theme.Graphite
import com.sonder.peeker.presentation.ui.theme.Gray
import com.sonder.peeker.presentation.ui.theme.Pink
import com.sonder.peeker.presentation.ui.theme.White

@Composable
fun NotificationSettingsScreen(
    navController: NavController,
    viewModel: NotificationSettingViewModel = hiltViewModel()
) {
    Scaffold(
        floatingActionButton = {
            if (!viewModel.state.value.isLoading && viewModel.hasChanged())
                FloatingActionButton(onClick = { viewModel.updateUser(navController) }) {
                    Icon(
                        imageVector = Icons.Rounded.Save,
                        contentDescription = "Actualizar Ajustes"
                    )
                }

        },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column() {
            NotificationSettingsTitle(navController)
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
fun NotificationSettingsTitle(
    navController: NavController,
    viewModel: NotificationSettingViewModel = hiltViewModel(),
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
                    navController.navigate(Screen.NotificationsScreen.route)
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
            Text(text = "Ajustes de Notificación", style = MaterialTheme.typography.h2)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotificationPreview(
    navController: NavController,
    viewModel: NotificationSettingViewModel
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
        else {
            Box(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 30.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                )
                {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (viewModel.getNotificationEnabled()) Text("Notificaciones Habilitadas") else Text(
                            "Notificaciones Deshabilitadas"
                        )
                        Switch(checked = viewModel.getNotificationEnabled(),
                            onCheckedChange = { viewModel.toggleNotificationEnabled() })
                    }
                    if (viewModel.isNotifying())
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text("Notificar ${viewModel.getDaysToExpire()} días antes")
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(onClick = {
                                    viewModel.setDaysToExpire(viewModel.getDaysToExpire() - 1)
                                }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Remove,
                                        contentDescription = "Remove One",
                                        tint = Graphite
                                    )
                                }
                                Text(
                                    "${viewModel.getDaysToExpire()}",
                                    style = MaterialTheme.typography.h2,
                                )

                                Button(onClick = {
                                    viewModel.setDaysToExpire(viewModel.getDaysToExpire() + 1)
                                }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Add,
                                        contentDescription = "Add One",
                                        tint = Graphite
                                    )
                                }

                            }
                        }

                }
            }
        }
    }
}