package com.sonder.peeker.presentation.document_create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sonder.peeker.presentation.document_list.DocumentCreateViewModel
import com.sonder.peeker.presentation.document_list.DocumentListScreen
import com.sonder.peeker.presentation.ui.theme.Gray
import com.sonder.peeker.presentation.ui.theme.GreetingSection
import com.sonder.peeker.presentation.ui.theme.Pink
import com.sonder.peeker.presentation.ui.theme.White

@Composable
fun DocumentCreateScreen(
    viewModel: DocumentCreateViewModel = hiltViewModel()
) {
    Scaffold(
        // TODO: Floating on top of form.
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.createDocument()
                }) {
                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                    contentDescription = "Crear Documento"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Column {
                // TODO: Navigate Back.
                Icon(
                    Icons.Rounded.ChevronLeft,
                    contentDescription = "Volver"
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Nuevo documento", style = MaterialTheme.typography.h2)
                    Text(
                        text = "Rellena los campos para poder crear un documento.",
                        style = MaterialTheme.typography.body1,
                        color = White
                    )
                }
                TextField(
                    value = viewModel.state.value.documentName ?: "",
                    onValueChange = { newValue -> viewModel.setDocumentName(newValue) },
                    label = { Text(text = "Nombre del documento") },
                    placeholder = { Text(text = "Mi pasaporte") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )
            }
        }
    }
}