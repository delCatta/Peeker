package com.sonder.peeker.presentation.document_list.document_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sonder.peeker.core.Constants.UNEXPECTER_ERROR
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.presentation.Screen
import com.sonder.peeker.presentation.ui.theme.White

@Composable
fun DocumentScreen(
    navController: NavController,
    viewModel: DocumentViewModel = hiltViewModel()

) {
    var state = viewModel.state.value

    Scaffold(
        floatingActionButton = {
            if (!state.isLoading) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.NewDocumentScreen.route)
                    }) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Editar Documento"
                    )
                }
            } else CircularProgressIndicator()
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
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
                if (state.isLoading || !state.error.isNullOrEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 30.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Documento no encontrado",
                            style = MaterialTheme.typography.h2
                        )
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (state.isLoading) CircularProgressIndicator() else Text(
                                state.error ?: UNEXPECTER_ERROR
                            )
                        }
                    }
                } else {
                    var document = state.document!!
                    DocumentInformation(navController, document, viewModel)
                }
            }
        }
    }
}

@Composable
fun DocumentInformation(
    navController: NavController,
    document: Document,
    viewModel: DocumentViewModel

) {
    // TODO Diego: Mejorar esta lista (apreta en ver en un documento)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = document?.name ?: "Documento no encontrado",
            style = MaterialTheme.typography.h2
        )
        Text(
            text = document?.description ?: "",
            style = MaterialTheme.typography.body1,
            color = White
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
            Column() {
                Text(document.document_type)
            }
        }
    }


}