package com.sonder.peeker.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sonder.peeker.core.Constants.UNEXPECTER_ERROR
import com.sonder.peeker.presentation.Screen
import com.sonder.peeker.presentation.authentication.login.LoginViewModel
import com.sonder.peeker.presentation.authentication.register.RegisterViewModel
import com.sonder.peeker.presentation.ui.components.RoundedButton
import com.sonder.peeker.presentation.ui.components.TransparentTextField
import com.sonder.peeker.presentation.ui.theme.Pink
import com.sonder.peeker.presentation.ui.theme.White

@Composable
fun RegistrationScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navController.navigate(Screen.LoginScreen.route)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Icon",
                        tint = MaterialTheme.colors.primary
                    )
                }

                Text(
                    text = "Crea tu cuenta",
                    style = MaterialTheme.typography.h5.copy(
                        color = MaterialTheme.colors.primary
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TransparentTextField(
                    textFieldValue = viewModel.nameValue,
                    textLabel = "Nombre",
                    keyboardType = KeyboardType.Text,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    imeAction = ImeAction.Next
                )
                TransparentTextField(
                    textFieldValue = viewModel.lastNameValue,
                    textLabel = "Apellidos",
                    keyboardType = KeyboardType.Text,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    imeAction = ImeAction.Next
                )

                TransparentTextField(
                    textFieldValue = viewModel.emailValue,
                    textLabel = "Email",
                    keyboardType = KeyboardType.Email,
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    imeAction = ImeAction.Next
                )

                TransparentTextField(
                    textFieldValue = viewModel.passwordValue,
                    textLabel = "Contraseña",
                    keyboardType = KeyboardType.Password,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    imeAction = ImeAction.Next,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordVisibility = !passwordVisibility
                            }
                        ) {
                            Icon(
                                imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle Password Icon"
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
                )

                TransparentTextField(
                    textFieldValue = viewModel.confirmPasswordValue,
                    textLabel = "Confirmar Contraseña",
                    keyboardType = KeyboardType.Password,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            viewModel.register(onSuccess = {
                                onRegistrationSuccess(viewModel, loginViewModel, navController)
                            })
                        }
                    ),
                    imeAction = ImeAction.Done,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                confirmPasswordVisibility = !confirmPasswordVisibility
                            }
                        ) {
                            Icon(
                                imageVector = if (confirmPasswordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle Password Icon"
                            )
                        }
                    },
                    visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation()
                )

                if (!viewModel.state.value.error.isNullOrEmpty())
                    Spacer(modifier = Modifier.height(16.dp))
                if (!viewModel.state.value.error.isNullOrEmpty())
                    Text(
                        viewModel.state.value.error ?: UNEXPECTER_ERROR,
                        color = Pink
                    )

                Spacer(modifier = Modifier.height(16.dp))


                if (viewModel.state.value.isLoading ?: false)
                    CircularProgressIndicator()
                else RoundedButton(
                    text = "Unirse a Peeker",
                    displayProgressBar = false,
                    onClick = {
                        viewModel.register(onSuccess = {
                            onRegistrationSuccess(viewModel, loginViewModel, navController)
                        })
                    })

                ClickableText(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = White,
                            )
                        ) {
                            append("¿Ya tienes una cuenta?\u00A0")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colors.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Iniciar Sesión")
                        }
                    },
                    onClick = {
                        navController.navigate(Screen.LoginScreen.route)
                    }
                )
            }

        }
    }
}

fun onRegistrationSuccess(
    viewModel: RegisterViewModel,
    loginViewModel: LoginViewModel,
    navController: NavController
) {
    val email = viewModel.emailValue.value
    val password = viewModel.passwordValue.value
    loginViewModel.setCredentials(email, password)
    loginViewModel.login(navController)
}