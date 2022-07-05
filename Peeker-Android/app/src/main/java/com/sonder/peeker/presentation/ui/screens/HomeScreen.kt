package com.sonder.peeker.presentation.ui.theme

import android.content.Intent
import android.provider.DocumentsContract
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sonder.peeker.core.Constants.UNEXPECTER_ERROR
import com.sonder.peeker.di.SessionManager
import com.sonder.peeker.presentation.Screen
import com.sonder.peeker.presentation.authentication.AuthViewModel
import com.sonder.peeker.presentation.authentication.login.LoginViewModel
import com.sonder.peeker.presentation.document_create.DocumentCreateViewModel
import com.sonder.peeker.presentation.document_list.DocumentListScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.security.AccessController.getContext

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
    createViewModel: DocumentCreateViewModel = hiltViewModel()
) {
    var isAuth = viewModel.state.value.isAuth
    var isLoading = viewModel.state.value.isLoading
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
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
    } else
        ModalBottomSheetLayout(
            sheetContent = {
                BottomSheetContent(navController, scope, modalBottomSheetState, createViewModel)
            },
            sheetState = modalBottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            //        sheetBackgroundColor = colorResource(id = R.color.colorPrimary),
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
        ) {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                modalBottomSheetState.show()
                            }
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
                        GreetingSection(name = viewModel.getUserName(), logout = {
                            viewModel.logOut()
                            navController.navigate(Screen.LoginScreen.route)
                        }, navController)
                        DocumentListScreen(navController)
                    }
                }

            }
        }
}

@Composable
fun GreetingSection(
    name: String = "A Peeker",
    logout: () -> (Unit),
    navController: NavController
) {
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
                    navController.navigate(Screen.NotificationsScreen.route)
                },
            ) {
                Icon(
                    Icons.Outlined.NotificationsNone,
                    contentDescription = "Notificaciones",
                    tint = Pink
                )
                // TODO: New Notifications Amount Icons
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(
    navController: NavController,
    scope: CoroutineScope,
    state: ModalBottomSheetState,
    viewModel: DocumentCreateViewModel
) {
    val documentState = viewModel.state.value
    val pickPictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { fileUri ->
        if (fileUri != null) {
            Log.d("FileUri",fileUri.path.toString())
            viewModel.uploadFile(fileUri,navController)
        }
    }
    Column {
        if (documentState.isLoading || !documentState.error.isNullOrEmpty())
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            ) {
                if (!documentState.error.isNullOrEmpty()) {
                    Text(documentState.error, color = Pink)
                } else {
                    CircularProgressIndicator()
                }
            } else {
            BottomSheetListItem(
                icon = Icons.Rounded.AttachFile,
                title = "Crear a partir de un Archivo.",
                onItemClick = {
                    pickPictureLauncher.launch("*/*")
                })
            BottomSheetListItem(
                icon = Icons.Rounded.Create,
                title = "Crear en blanco.",
                onItemClick = {
                    viewModel.createEmptyDocument(navController, onSuccess = {
                        scope.launch {
                            state.hide()
                        }
                    })
                })
            BottomSheetListItem(
                icon = Icons.Rounded.Cancel,
                title = "Cancelar",
                onItemClick = {
                    scope.launch {
                        state.hide()
                    }
                })
        }
    }
}

@Composable
fun BottomSheetListItem(icon: ImageVector, title: String, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(title) })
            .height(55.dp)
            .padding(start = 15.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = "Share")
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = title)
    }
}
