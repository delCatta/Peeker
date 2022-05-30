package com.sonder.peeker.presentation.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sonder.peeker.di.SessionManager
import com.sonder.peeker.presentation.Screen
import com.sonder.peeker.presentation.authentication.AuthViewModel
import com.sonder.peeker.presentation.authentication.login.LoginViewModel
import com.sonder.peeker.presentation.document_list.DocumentListScreen

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var isAuth = viewModel.state.value.isAuth
    var isLoading = viewModel.state.value.isLoading
    if (!isAuth) {
        Scaffold {
            if (isLoading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            } else {
                navController.navigate(Screen.LoginScreen.route)
            }
        }
    } else Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.NewDocumentScreen.route)
                }) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Crear Documento"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                GreetingSection(name = viewModel.getUserName(),logout = {
                    viewModel.logOut()
                    navController.navigate(Screen.LoginScreen.route)
                })
                DocumentListScreen(navController)
            }
        }

    }
}

@Composable
fun GreetingSection(name: String = "Diego Cattarinich Clavel", logout: () -> (Unit)) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(text = "Bienvenido/a", style = MaterialTheme.typography.body1, color = Pink)
            Text(text = name, style = MaterialTheme.typography.h2)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .padding(10.dp)
        ) {
            IconButton(
                onClick = {
                    // TODO: Navigate to Notifications
                },
            ) {
                Icon(
                    Icons.Outlined.NotificationsNone,
                    contentDescription = "Notificaciones",
                    tint = Pink
                )
            }
            IconButton(
                onClick = {
                    logout()
                },
            ) {
                Icon(
                    Icons.Outlined.Logout,
                    contentDescription = "Log Out",
                    tint = Pink
                )
            }
        }


    }
}


