package com.sonder.peeker.presentation.document_create

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.Size
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sonder.peeker.core.Constants.DOCUMENT_TYPES
import com.sonder.peeker.presentation.Screen
import com.sonder.peeker.presentation.document_create.DocumentCreateViewModel
import com.sonder.peeker.presentation.ui.theme.Gray
import com.sonder.peeker.presentation.ui.theme.GreetingSection
import com.sonder.peeker.presentation.ui.theme.Pink
import com.sonder.peeker.presentation.ui.theme.White
import java.util.*

@Composable
fun DocumentCreateScreen(
    navController: NavController,
    viewModel: DocumentCreateViewModel = hiltViewModel()
) {
    Scaffold(
        // TODO: Floating on top of form.
        floatingActionButton = {
            if (!viewModel.state.value.isLoading)
                FloatingActionButton(
                    onClick = {
                        viewModel.createDocument {
                            navController.navigate(Screen.HomeScreen.route)
                        }
                    }) {
                    Icon(
                        imageVector = Icons.Rounded.ChevronRight,
                        contentDescription = "Crear Documento"
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
                    //label = { Text(text = viewModel.state.value.documentType?: "") },
                    placeholder = { Text(text = "Mi pasaporte") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )
                TextField(
                    value = viewModel.state.value.documentDescription ?: "",
                    onValueChange = { newValue -> viewModel.setDocumentDescription(newValue) },
                    label = { Text(text = "Descripción del documento") },
                    placeholder = { Text(text = "Mi pasaporte") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )
                dropDownMenu(viewModel)

                showDateOfIssuePicker(
                    context = LocalContext.current,
                    viewModel = viewModel,
                    text = "Fecha de emisión"
                )

                showExpirationDatePicker(
                    context = LocalContext.current,
                    viewModel = viewModel,
                    text = "Fecha de expiración"
                )


                if (!viewModel.state.value.error.isNullOrBlank())
                    Text(text = viewModel.state.value.error ?: "", color = Pink)


            }
        }
    }
}

@Composable
fun dropDownMenu(viewModel: DocumentCreateViewModel) {

    var expanded by remember { mutableStateOf(false) }
    val suggestions = DOCUMENT_TYPES
    var textfieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    OutlinedTextField(value = suggestions[viewModel.state.value.documentType ?: 0],
        onValueChange = { newValue ->
            viewModel.setDocumentType(
                suggestions.indexOf(newValue),
                newValue
            )
        },
        enabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                //This value is used to assign to the DropDown the same width
                textfieldSize = coordinates.size.toSize()
            },
        label = { Text("Tipo del documento") },
        trailingIcon = {
            Icon(icon, "contentDescription",
                Modifier.clickable { expanded = !expanded })
        })
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
            .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
    ) {
        suggestions.forEachIndexed { index, label ->
            DropdownMenuItem(onClick = {
                viewModel.setDocumentType(index, label)
                expanded = false
            }) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.body1,
                    color = White
                )
            }
        }
    }
}

@Composable
fun showDateOfIssuePicker(context: Context, viewModel: DocumentCreateViewModel, text: String) {

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val date = remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            viewModel.setDocumentDateOfIssue("$dayOfMonth-$month-$year")
        },
        year,
        month,
        day
    )

    Text(text = "$text: ${viewModel.state.value.documentDateOfIssue ?: ""}")
    Button(onClick = {
        datePickerDialog.show()
    }) {
        Text(text = "Open Date Picker")
    }
}

@Composable
fun showExpirationDatePicker(context: Context, viewModel: DocumentCreateViewModel, text: String) {

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val date = remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            viewModel.setDocumentExpirationDate("$dayOfMonth-$month-$year")
        },
        year,
        month,
        day
    )

    Text(text = "$text: ${viewModel.state.value.documentExpirationDate ?: ""}")
    Button(onClick = {
        datePickerDialog.show()
    }) {
        Text(text = "Open Date Picker")
    }
}


