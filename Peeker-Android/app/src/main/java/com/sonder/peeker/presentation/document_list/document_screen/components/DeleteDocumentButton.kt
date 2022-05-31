package com.sonder.peeker.presentation.document_list.document_screen.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.sonder.peeker.presentation.document_update.DocumentUpdateViewModel

@Composable
fun DeleteDocumentButton(
    onSuccess: () -> (Unit)?,
    viewModel: DocumentUpdateViewModel = hiltViewModel()
) {


    if (viewModel.state.value.isLoading)
        CircularProgressIndicator()
    else IconButton(
        onClick = {
            Log.d("Delete", "Button Clicked")
            viewModel.deleteDocument({
                onSuccess()!!
            }, {
                // TODO: Toast con el viewmodel.state.value.error
            })
        }
    ) {
        Icon(
            imageVector = Icons.Default.DeleteOutline,
            contentDescription = "Delete Icon",
            tint = MaterialTheme.colors.primary
        )
    }
}