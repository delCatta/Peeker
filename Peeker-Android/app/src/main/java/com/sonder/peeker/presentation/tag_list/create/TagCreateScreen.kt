package com.sonder.peeker.presentation.tag_list.create
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sonder.peeker.R
import com.sonder.peeker.core.Constants.DOCUMENT_TYPES
import com.sonder.peeker.presentation.Screen
import com.sonder.peeker.presentation.ui.theme.Pink
import com.sonder.peeker.presentation.ui.theme.White
import java.util.*

@Composable
fun TagCreateScreen(
    navController: NavController,
    viewModel: TagCreateViewModel = hiltViewModel()
) {
    Scaffold(
        floatingActionButton = {
            if (!viewModel.state.value.isLoading)
                FloatingActionButton(
                    onClick = {
                        viewModel.createTag {
                            navController.navigate(Screen.HomeScreen.route)
                        }
                    }) {
                    Icon(
                        imageVector = Icons.Rounded.ChevronRight,
                        contentDescription = "Crear Tag"
                    )
                }
            else
                CircularProgressIndicator()
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            if (!viewModel.state.value.isLoading)
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 30.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "Crear un tag", style = MaterialTheme.typography.h2)
                        Text(
                            text = "Escribe el nombre de tu nuevo tag.",
                            style = MaterialTheme.typography.body1,
                            color = White
                        )
                    }
                    TextField(
                        value = viewModel.state.value.tagName ?: "",
                        onValueChange = { newValue -> viewModel.setTagName(newValue) },
                        label = { Text(text = "Nombre del tag") },
                        //label = { Text(text = viewModel.state.value.documentType?: "") },
                        placeholder = { Text(text = "Personal") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )

                    if (!viewModel.state.value.error.isNullOrBlank())
                        Text(text = viewModel.state.value.error ?: "", color = Pink)

                }else Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
    }
}


