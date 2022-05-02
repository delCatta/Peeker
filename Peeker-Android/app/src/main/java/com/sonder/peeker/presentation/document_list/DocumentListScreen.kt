package com.sonder.peeker.presentation.document_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.sonder.peeker.presentation.document_list.components.DocumentSelector
import com.sonder.peeker.presentation.ui.theme.Gray

@Composable
fun DocumentListScreen(
//    navController: NavController,
    viewModel: DocumentListViewModel = hiltViewModel()
) {
    DocumentSelector()
    DocumentPreview()
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
        // TODO: Check if viewModel is loading documents.
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(viewModel.documents().size) { DocumentItem(viewModel.documents()[it]) }
        }
    }
}

@Composable
fun DocumentItem(
    document: String
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
        ){
            Text(
                text = document,
                style = MaterialTheme.typography.h2,
                lineHeight = 26.sp,
                modifier = Modifier.align(
                    Alignment.TopStart
                )
            )
        }

    }
}