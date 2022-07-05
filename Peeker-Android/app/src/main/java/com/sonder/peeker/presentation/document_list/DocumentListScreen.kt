package com.sonder.peeker.presentation.document_list

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.presentation.Screen
import com.sonder.peeker.presentation.document_list.components.DocumentSelector
import com.sonder.peeker.presentation.ui.theme.Gray
import com.sonder.peeker.presentation.ui.theme.Pink
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

val today: String = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now())

@Composable
fun DocumentListScreen(
    navController: NavController,
    viewModel: DocumentListViewModel = hiltViewModel()
) {
    DocumentSelector(navController)
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

        if (!viewModel.state.value.error.isNullOrEmpty())
            Text(
                viewModel.state.value.error,
                style = MaterialTheme.typography.body1,
                color = Pink,
                modifier = Modifier.padding(15.dp)
            )
        else if (viewModel.documents().isNullOrEmpty()) {
            Text(
                "No se encontraron archivos",
                style = MaterialTheme.typography.body1,
                color = Pink,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 64.dp)
            )

        } else {
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
                modifier = Modifier.fillMaxHeight(),
            ) {
                items(viewModel.documents().size) {
                    var document: Document? = getDocumentAtIndex(viewModel.documents(), it)
                    if (document != null) DocumentItem(
                        navController,
                        document
                    )
                }
            }
        }
    }
}

@Composable
fun DocumentItem(
    navController: NavController,
    document: Document,
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 5.dp)
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
                        text = document.name ?: "Sin título",
                        style = MaterialTheme.typography.h6,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = document.description ?: "",
                        style = MaterialTheme.typography.body2,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        run {
                            val expiredDay =
                                document.expiration_date?.split("T")?.get(0)
                            val day = expiredDay?.split("-")?.get(2)
                            val month = expiredDay?.split("-")?.get(1)
                            val year = expiredDay?.split("-")?.get(0)


                            if (expiredDay?.isNotEmpty() == true) if (today > expiredDay.toString())  "Expiró el ${if (day?.length == 2 && day[0] != '0') day else day?.get(1)} de ${if (month?.length == 2 && month[0] != '0') month else month?.get(1)} del ${year}"
                            else "Expira el ${if (day?.length == 2 && day[0] != '0') day else day?.get(1)} de ${if (month?.length == 2 && month[0] != '0') month else month?.get(1)} del ${year}" else ""
                        },
                        style = MaterialTheme.typography.body2,
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(70.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, false)
                ) {
                    Button(onClick = {

                    }, modifier = Modifier.size(10.dp),
                        colors = ButtonDefaults.buttonColors( backgroundColor = run {
                            val expiredDay =
                                document.expiration_date?.split("T")?.get(0)
                            val year = expiredDay?.split("-")?.get(0)
                            val month = expiredDay?.split("-")?.get(1)
                            val day = expiredDay?.split("-")?.get(2)
                            if (expiredDay?.isNotEmpty() == true) if (today > expiredDay.toString()) Color.Red else if (-ChronoUnit.DAYS.between(day?.let { month?.let { it1 -> year?.let { it2 ->
                                    LocalDate.of(
                                        it2.toInt(), it1.toInt(), it.toInt())
                                } } }, LocalDate.of(
                                    today.split("-")[0].toInt(),
                                    today.split("-")[1].toInt(), today.split("-")[2].toInt())) <= 30) Color.Yellow else Color.Green else Color.DarkGray
                        }), shape = CircleShape
                    ){
                        Text("")
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

fun getDocumentAtIndex(documents: List<Document>, index: Int): Document? {
    try {
        return documents[index]
    } catch (ex: Exception) {
        Log.d("Document At Index", ex.toString())
        return null
    }
}