package com.sonder.peeker.presentation.document_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.presentation.Screen
import com.sonder.peeker.presentation.document_list.components.DocumentSelector
import com.sonder.peeker.presentation.ui.theme.Gray
import com.sonder.peeker.presentation.ui.theme.Pink

@Composable
fun DocumentListScreen(
    navController: NavController,
    viewModel: DocumentListViewModel = hiltViewModel()
) {
    DocumentSelector()
    if (!viewModel.state.value.isLoading)
        DocumentPreview(navController, viewModel)
    else Box(
        modifier = Modifier
            .padding(15.dp)
            .width(30.dp)
            .height(30.dp)
    ) { CircularProgressIndicator() }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DocumentPreview(
    navController: NavController,
    viewModel: DocumentListViewModel
) {
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
                modifier = Modifier.fillMaxHeight(),
            ) {
                items(viewModel.documents().size) {
                    DocumentItem(
                        navController,
                        viewModel.documents()[it]
                    )
                }
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
    navController: NavController,
    document: Document
) {
    BoxWithConstraints(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(15.dp))
            .background(Gray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 15.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = document.name,
                        style = MaterialTheme.typography.h6,
                    )
                    Text(
                        text = document.description,
                        style = MaterialTheme.typography.body2,
                    )
                    Text(
                        "Expira el ${document.expiration_date.split("T")[0]}", // TODO Diego: Pasar de Timestamp String a 'dd del mm, yyyy'
                        style = MaterialTheme.typography.body2,
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, false)
                ) {


                    IconButton(onClick = {
                        // TODO: toggle Favorite
                    }) {
                        Icon(
                            // TODO: If favorite
                            Icons.Outlined.Favorite,
                            contentDescription = "Notificaciones",
                            tint = Pink
                        )
                    }
                    Button(
                        onClick = {
                            navController.navigate(Screen.DocumentScreen.route + "/${document.id}")
                        }) {
                        Text("Ver")
                    }

                }
            }

        }

    }
}