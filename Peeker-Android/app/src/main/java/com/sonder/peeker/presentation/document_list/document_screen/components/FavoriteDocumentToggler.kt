package com.sonder.peeker.presentation.document_list.document_screen.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.presentation.document_list.DocumentListViewModel
import com.sonder.peeker.presentation.document_list.document_screen.DocumentViewModel
import com.sonder.peeker.presentation.ui.theme.Pink

@Composable
fun FavoriteDocumentToggler(
    viewModel: DocumentViewModel
) {
    val state = viewModel.state.value
    val document = state.document
    if (state.isLoading) CircularProgressIndicator()
    else
        IconButton(onClick = {
            viewModel.toggleFavorite(document!!)
        }) {
            Icon(
                if (document?.favorite?:false) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Notificaciones",
                tint = Pink
            )
        }
}
