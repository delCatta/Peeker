package com.sonder.peeker.presentation.document_list.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Colors
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sonder.peeker.presentation.document_list.DocumentListViewModel
import com.sonder.peeker.presentation.ui.theme.Gray
import com.sonder.peeker.presentation.ui.theme.Pink
import com.sonder.peeker.presentation.ui.theme.White

@Composable
fun DocumentSelector(
    viewModel: DocumentListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    // TODO: Have first items to scroll with Tags.
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SelectorChip(
            isSelected = state.favoritesSelected,
            onPressed = { viewModel.selectFavorites() }) {
            Icon(
                if (state.favoritesSelected) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                contentDescription = "Favorites",
                tint = if (state.favoritesSelected) Gray else White
            )
        }
        SelectorChip(
            isSelected = state.expiredSelected,
            onPressed = { viewModel.selectExpired() }) {
            Icon(
                if (state.expiredSelected) Icons.Rounded.Error else Icons.Outlined.ErrorOutline,
                contentDescription = "Expired",
                tint = if (state.expiredSelected) Gray else White
            )
        }
        SelectorChip(
            isSelected = state.allSelected,
            text = "Documents",
            onPressed = { viewModel.selectAll() }) {}

        if (!state.isLoadingTags)
            LazyRow(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items(state.tags.size) {
                    SelectorChip(
                        isSelected = state.selectedTagIndex == it,
                        text = state.tags[it],
                        onPressed = { viewModel.selectTag(it) }) {}
                }
            } else Box(modifier = Modifier.padding(15.dp).width(30.dp).height(30.dp)){CircularProgressIndicator()}
    }
}

@Composable
fun SelectorChip(
    onPressed: () -> Unit,
    backgroundColor: Color? = null,
    textColor: Color? = null,
    isSelected: Boolean,
    text: String? = null,
    content: @Composable() () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
            .clickable { onPressed() }
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor ?: if (isSelected) Pink else Gray)
            .padding(15.dp)
    ) {
        if (!text.isNullOrEmpty())
            Text(
                text = text,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = textColor ?: if (isSelected) Gray else White
            )
        else content()
    }
}