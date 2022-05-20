package com.sonder.peeker.presentation.document_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.presentation.document_list.components.DocumentSelector
import com.sonder.peeker.presentation.ui.theme.Gray
import com.sonder.peeker.presentation.ui.theme.Pink

@Composable
fun DocumentListScreen(
//    navController: NavController,
    viewModel: DocumentListViewModel = hiltViewModel()
) {
    DocumentSelector()
    if (!viewModel.state.value.isLoading)
        DocumentPreview()
    else Box(
        modifier = Modifier
            .padding(15.dp)
            .width(30.dp)
            .height(30.dp)
    ) { CircularProgressIndicator() }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DocumentPreview(viewModel: DocumentListViewModel = hiltViewModel()) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            viewModel.selectedTitle(),
            style = MaterialTheme.typography.h1,
            modifier = Modifier.padding(15.dp)
        )
        if (viewModel.state.value.error.isNullOrEmpty())
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(viewModel.documents().size) { DocumentItem(viewModel.documents()[it]) }
            } else Text(
            viewModel.state.value.error,
            style = MaterialTheme.typography.body1,
            color = Pink,
            modifier = Modifier.padding(15.dp)
        )
    }
}

@Composable
fun DocumentItem(
    document: Document
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(15.dp))
            .background(Gray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(
                text = document.name,
                style = MaterialTheme.typography.h2,
                lineHeight = 26.sp,
                modifier = Modifier.align(
                    Alignment.TopStart
                )
            )
        }

    }
}