package com.sonder.peeker.presentation.document_list.document_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sonder.peeker.core.Constants.UNEXPECTER_ERROR
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.presentation.Screen
import com.sonder.peeker.presentation.document_list.document_screen.components.FavoriteDocumentToggler
import com.sonder.peeker.presentation.ui.theme.Gray
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
                         navController.navigate(Screen.UpdateDocumentScreen.route + "/${state.document?.id?:""}")
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
                .fillMaxHeight()
                .padding(15.dp)
        ) {
            Column {
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
                    FavoriteDocumentToggler(viewModel)

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
                    DocumentList(navController, document, viewModel)
                }
            }
        }
    }
}

@Composable
fun DocumentList(
    navController: NavController,
    document: Document,
    viewModel: DocumentViewModel

) {
    Box(
        modifier = Modifier
            .aspectRatio(0.8f)
            .clip(RoundedCornerShape(15.dp))
            .background(Gray)
            .fillMaxHeight()
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 30.dp)
                .padding(vertical = 30.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = document?.name ?: "Documento no encontrado",
                style = MaterialTheme.typography.h2
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tipo",
                    style = MaterialTheme.typography.body1,
                    color = White
                )
                Text(
                    // TODO Diego: Agregar el tipo del documento
                    text = "",
                    style = MaterialTheme.typography.body1,
                    color = White
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Fecha de emisión",
                    style = MaterialTheme.typography.body1,
                    color = White
                )
                Text(
                    text = document?.emission_date.split("T")[0] ?: "",
                    style = MaterialTheme.typography.body1,
                    color = White
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Fecha de vencimiento",
                    style = MaterialTheme.typography.body1,
                    color = White
                )
                Text(
                    text = document?.expiration_date.split("T")[0] ?: "",
                    style = MaterialTheme.typography.body1,
                    color = White
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Fecha de creación",
                    style = MaterialTheme.typography.body1,
                    color = White
                )
                Text(
                    text = document?.created_at.split("T")[0] ?: "",
                    style = MaterialTheme.typography.body1,
                    color = White
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Fecha de actualización",
                    style = MaterialTheme.typography.body1,
                    color = White
                )
                Text(
                    text = document?.updated_at.split("T")[0] ?: "",
                    style = MaterialTheme.typography.body1,
                    color = White
                )
            }
            Text(
                text = "Descripción",
                style = MaterialTheme.typography.h3
            )
            Text(
                text = document?.description ?: "",
                style = MaterialTheme.typography.body1,
                color = White
            )

        }
    }
}