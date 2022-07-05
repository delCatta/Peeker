package com.sonder.peeker.presentation.tag_list

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.domain.model.Tag
import com.sonder.peeker.presentation.Screen
import com.sonder.peeker.presentation.authentication.AuthViewModel
import com.sonder.peeker.presentation.ui.theme.Gray
import com.sonder.peeker.presentation.ui.theme.Pink
import com.sonder.peeker.presentation.ui.theme.White

@Composable
fun TagListScreen(
    navController: NavController,
    viewModel: TagListViewModel = hiltViewModel()
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                        navController.navigate(Screen.TagCreateScreen.route)
                }) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Crear Tag"
                )
            }},
            modifier = Modifier
                .fillMaxWidth()
            ) {
            Column() {
                TagTitle(navController)
                if (!viewModel.state.value.isLoading)
                    TagPreview(navController, viewModel)
                else Box(
                    modifier = Modifier
                        .padding(15.dp)
                        .width(30.dp)
                        .height(30.dp)
                ) { CircularProgressIndicator() }
            }
        }
        }

        @Composable
        fun TagTitle(
            navController: NavController,
            viewModel: TagListViewModel = hiltViewModel(),
            authViewModel: AuthViewModel = hiltViewModel(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),

                ) {
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
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = authViewModel.getUserName(),
                        style = MaterialTheme.typography.body1,
                        color = Pink
                    )
                    Text(text = "Tags", style = MaterialTheme.typography.h2)
                }
            }
        }

        @OptIn(ExperimentalFoundationApi::class)
        @Composable
        fun TagPreview(
            navController: NavController,
            viewModel: TagListViewModel
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 32.dp)
            ) {
                if (!viewModel.state.value.error.isNullOrEmpty())
                    Text(
                        viewModel.state.value.error,
                        style = MaterialTheme.typography.body1,
                        color = Pink,
                        modifier = Modifier.padding(15.dp)
                    )
                else if (viewModel.tags().isNullOrEmpty()) {
                    Text(
                        "No tienes tags",
                        style = MaterialTheme.typography.body1,
                        color = Pink,
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 64.dp)
                    )

                } else {
                    Box(
                        modifier = Modifier.padding(horizontal = 15.dp)
                    ) {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        )
                        {

                            items(viewModel.tags().size) {
                                var tag: Tag? =
                                    getTagAtIndex(viewModel.tags(), it)
                                if (tag != null) TagItem(
                                    navController,
                                    viewModel,
                                    tag
                                )
                            }
                        }
                    }
                }
            }
        }

        @Composable
        fun TagItem(
            navController: NavController,
            viewModel: TagListViewModel,
            tag: Tag,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(Gray),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            tag.name,
                            style = MaterialTheme.typography.h4,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            color = White,
                        )
                        Button(
                            onClick = {viewModel.deleteTag(tag.id)}

                        ){
                            Text("Borrar", color = Color.Black)
                        }
                    }
                }
            }
        }

        fun getTagAtIndex(tags: List<Tag>, index: Int): Tag? {
            try {
                return tags[index]
            } catch (ex: Exception) {
                Log.d("Tag At Index", ex.toString())
                return null
            }
        }